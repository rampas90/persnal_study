원격저장소로 GitHub을 이용하고 있고, Branch를 만들고 삭제하는 방법에 대해서 알아보자. 

1) 브랜치 생성, 삭제 순서 
  - local 저장소에 branch를 만든다 
  - remote 저장소로 branch를 push 하여 추적(tracking) 한다 
  - 사용하지 않는 branch는 삭제한다 
////////////////////////////////////////////////
// 새로운 브랜치를 생성하고 checkout 한다 
$ git checkout -b shopping_cart
Switched to a new branch 'shopping_cart'

////////////////////////////////////////////////
// 원격 저장소로 브랜치를 push 한다 
$ git push origin shopping_cart
Username for 'https://github.com':
Password for 'https://ysyun@yuwin.co.kr@github.com':
Total 0 (delta 0), reused 0 (delta 0)
To https://github.com/ysyun/pro_git.git
 * [new branch]      shopping_cart -> shopping_cart

// github에 새로운 브랜치가 추가 되었다. 


///////////////////////////////////////////////
// 새로운 파일을 추가하고 원격으로 push 한다 
$ touch cart.js
$ git add cart.js
$ git commit -m "add cart javascript file"
[shopping_cart 4856f8d] add cart javascript file
 0 files changed
 create mode 100644 cart.js

$ git push
Username for 'https://github.com':
Password for 'https://ysyun@yuwin.co.kr@github.com':
Counting objects: 3, done.
Delta compression using up to 2 threads.
Compressing objects: 100% (2/2), done.
Writing objects: 100% (2/2), 245 bytes, done.
Total 2 (delta 1), reused 0 (delta 0)
To https://github.com/ysyun/pro_git.git
   f42a865..4856f8d  shopping_cart -> shopping_cart


///////////////////////////////////////////////
// 로컬 브랜치 내역 과 원격 브랜치 내역을 본다 
$ git branch
  master
* shopping_cart

$ git branch -r
  origin/HEAD -> origin/master
  origin/master
  origin/shopping_cart

////////////////////////////////////////////////////
// 로컬의 새로 추가한 shopping_cart 브랜치를 삭제한다
$ git checkout master
Switched to branch 'master'

$ git branch -d shopping_cart
error: The branch 'shopping_cart' is not fully merged.
If you are sure you want to delete it, run 'git branch -D shopping_cart'.

$ git branch -D shopping_cart
Deleted branch shopping_cart (was 4856f8d).

$ git branch
* master

///////////////////////////////////////////////
// 로컬에 이제 shopping_cart 브랜치가 없다 다시 복구 하고 싶다면
// 원격 저장소를 통하여 checkout 하면 된다 
$ git checkout shopping_cart
Branch shopping_cart set up to track remote branch shopping_cart from origin.
Switched to a new branch 'shopping_cart'

$ git branch
  master
* shopping_cart

///////////////////////////////////////////////
// 원격의 브랜치 내역을 보자
// 어떤 브랜치들이 존재하는지 한눈에 알 수 있다 
$ git remote show origin
* remote origin
  Fetch URL: https://github.com/ysyun/pro_git.git
  Push  URL: https://github.com/ysyun/pro_git.git
  HEAD branch: master
  Remote branches:
    master        tracked
    shopping_cart tracked
  Local branches configured for 'git pull':
    master        merges with remote master
    shopping_cart merges with remote shopping_cart
  Local refs configured for 'git push':
    master        pushes to master        (up to date)
    shopping_cart pushes to shopping_cart (up to date)

///////////////////////////////////////////////
// 원격에 있는 브랜치를 삭제 하자 
$ git push origin :shopping_cart
Username for 'https://github.com':
Password for 'https://ysyun@yuwin.co.kr@github.com':
To https://github.com/ysyun/pro_git.git
 - [deleted]         shopping_cart

// shopping_cart 브랜치가 삭제되었음을 알 수 있다
$ git remote show origin
* remote origin
  Fetch URL: https://github.com/ysyun/pro_git.git
  Push  URL: https://github.com/ysyun/pro_git.git
  HEAD branch: master
  Remote branch:
    master tracked
  Local branch configured for 'git pull':
    master merges with remote master
  Local ref configured for 'git push':
    master pushes to master (up to date)

///////////////////////////////////////////////
// remote 브랜치 clean up 하기 
$ git remote prune origin

UserXP@NUKNALEA /d/Git_repositories/pro_git (master)
$ git remote show origin
* remote origin
  Fetch URL: https://github.com/ysyun/pro_git.git
  Push  URL: https://github.com/ysyun/pro_git.git
  HEAD branch: master
  Remote branch:
    master tracked
  Local branch configured for 'git pull':
    master merges with remote master
  Local ref configured for 'git push':
    master pushes to master (up to date)

2) master가 아닌 local 브랜치를 remote 저장소의 master 브랜치에 push 하기 
  - 조건 : local 저장소의 브랜치가 master가 아닌 다른 브랜치로 checkout 되어 있을 경우 예) shopping_cart
  - 명령 :  git push [원격저장소 주소 alias] [로컬저장소명칭]:master
  - 예 : git push origin shopping_cart:master
  - 의미 : origin 원격 주소의 master 브랜치에 local저장소의 shopping_cart 브랜치를 push 한다