public class GenericSample
{
	public static void main(String[] args) 
	{
		GenericSample sample = new GenericSample();
		sample.checkGenericDTO();

	}

	public void checkCastingDTO(){
		CastingDTO dto1=new CastingDTO();
		dto1.setObject(new String());

		CastingDTO dto2=new CastingDTO();
		dto2.setObject(new StringBuilder());

		CastingDTO dto3=new CastingDTO();
		dto3.setObject(new StringBuffer());
	}

	public void checkGenericDTO() {
		CastingGenericDTO<String> dto1=new CastingGenericDTO<String>();
		dto1.setObject(new String());
		CastingGenericDTO<StringBuffer> dto2=new CastingGenericDTO<StringBuffer>();
		dto2.setObject(new StringBuffer());
		CastingGenericDTO<StringBuilder> dto3=new CastingGenericDTO<StringBuilder>();
		dto3.setObject(new StringBuilder());

		String temp1=dto1.getObject();
		StringBuffer temp2=dto2.getObject();
		StringBuilder temp3=dto3.getObject();

	}

}