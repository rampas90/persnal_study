#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_HASH_BUCKET     10
#define MAX_HASH_KEY        MAX_HASH_BUCKET-1 
#define HASH_KEY_GET(data) 	data % MAX_HASH_BUCKET;
#define IS_SAME_NODE(a, b)  ( a->data == b )


/*
 * Return code.
 */
typedef enum
{
	RET_OK = 0, RET_NOK
} ret_code_t;


/*
 * hash node
 */
typedef struct _hash_node
{
    int    data;
    struct _hash_node *next;
} hash_node_t;


/*
 * hash hash table
 */
typedef struct _hash_table
{
    int                  key;

    hash_node_t         *right;
    struct _hash_table  *next;
} hash_table_t;


/*
 * MFG HASH TABLE
 */
hash_table_t hash_table[MAX_HASH_BUCKET];


/*
 * add node
 */
void add_node(int data, int key)
{
    hash_node_t *tmp_node;

    tmp_node = hash_table[key].right;
    if(tmp_node != NULL)
    {
        while(tmp_node->next != NULL)
        {
            tmp_node = tmp_node->next;
        }
        tmp_node->next = (hash_node_t *)malloc(sizeof(hash_node_t));
        tmp_node->next->data = data;
        tmp_node->next->next = NULL;
    }
    else
    {
        tmp_node = (hash_node_t *)malloc(sizeof(hash_node_t));
        tmp_node->data = data;
        tmp_node->next = NULL;

        hash_table[key].right  = tmp_node;
    }
}


/*
 * delete node
 */
int delete_node(int data, int key)
{
	hash_node_t *first_node = hash_table[key].right;
	hash_node_t *cur_node  = first_node;
	hash_node_t *pre_node  = NULL;


	if ( first_node == NULL )
		return RET_NOK;

	while ( cur_node )
	{
		if( IS_SAME_NODE(cur_node, data))
		{
			if( cur_node == first_node ) 
				hash_table[key].right = cur_node->next;
			else
				pre_node->next = cur_node->next;

			free(cur_node);
			return RET_OK;
		}

		pre_node = cur_node;
		cur_node = cur_node->next;
	}

	return RET_NOK;
}


/*
 * lookup node
 */
void *lookup_node(int data, int hash_key)
{
	hash_node_t *cur_node;
	int         ii;

	cur_node = hash_table[hash_key].right;
	while(cur_node != NULL)
	{
		if (IS_SAME_NODE(cur_node, data))
			return cur_node;

		cur_node = cur_node->next;
	}

	return NULL;
}


/*
 * display data
 */
void display_data()
{
	hash_node_t  *tmp_node;
	int          ii;

	printf("\n");

	for(ii=0; ii<MAX_HASH_BUCKET; ii++)
	{
		tmp_node = hash_table[ii].right;

		printf("hash_table[%3d]  :  ", ii);

		while(tmp_node!=NULL)
		{
			printf("%d  ",tmp_node->data);
			tmp_node = tmp_node->next;
		}
		printf("\n");
	}
}


/*
 * init hash table
 */
void create_hash_table()
{
	int  ii;

	memset(hash_table, 0, sizeof(hash_table));

	/* hash bucket except the last one */
	for(ii=0; ii<MAX_HASH_KEY; ii++)
	{
		hash_table[ii].key   = ii;
		hash_table[ii].right = NULL;
		hash_table[ii].next  = &hash_table[ii+1];
	}

	/* set last bucket */
	hash_table[MAX_HASH_KEY].key = MAX_HASH_KEY;
	hash_table[ii].right = NULL;
	hash_table[ii].next  = NULL;
}


/*
 * main
 */
void main()
{
	int number;
	int data, key;
	ret_code_t rc;

	/* init hash table */
	create_hash_table();

	for(;;)
	{
		printf("\n\n");
		printf("Command = {1(inseart)|2(delete)|3(display)|4(exit)}\n"); 
		printf("Enter Command  : ");
		scanf("%d", &number);

		switch(number)
		{
			case 1:
				printf("insert data    : ");
				scanf("%d", &data);

				key = HASH_KEY_GET(data);
				if ( lookup_node(data, key) != NULL )
				{
					printf("alread exist!!\n");
					continue;
				}

				add_node(data, key);
				break;

			case 2:
			{
				printf("delete data    : ");
				scanf("%d", &data);

				key = HASH_KEY_GET(data);
				rc = delete_node(data, key);
				if ( rc != RET_OK )
				{
					printf("not found\n");
				}
				break;
			}

			case 3:
				display_data();
				break;

			case 4:
				exit(1);

			default: 
				printf("Oops Wrong Number Entered!!\n\n");
				break;
		}
	}
}


/////////////////////////////////////////
/*
(17:26:46 AM) $ ./hash


Command = {1(inseart)|2(delete)|3(display)|4(exit)}
Enter Command : 1
insert data : 11


Command = {1(inseart)|2(delete)|3(display)|4(exit)}
Enter Command : 1
insert data : 1111


Command = {1(inseart)|2(delete)|3(display)|4(exit)}
Enter Command : 2
delete data : 1
not found


Command = {1(inseart)|2(delete)|3(display)|4(exit)}
Enter Command : 3

hash_table[ 0] :
hash_table[ 1] : 11 1111
hash_table[ 2] :
hash_table[ 3] :
hash_table[ 4] :
hash_table[ 5] :
hash_table[ 6] :
hash_table[ 7] :
hash_table[ 8] :
hash_table[ 9] :


Command = {1(inseart)|2(delete)|3(display)|4(exit)}
Enter Command : 4

*/