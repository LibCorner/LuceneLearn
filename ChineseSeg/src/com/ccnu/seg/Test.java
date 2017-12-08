package com.ccnu.seg;


import com.sun.jna.Library;
import com.sun.jna.Native;

public class Test {
	public interface CLibrary extends Library{
		CLibrary Instance=(CLibrary)Native.loadLibrary("lib/NLPIR", CLibrary.class);
		public int NLPIR_Init(byte[] sDataPath,int encoding,byte[] sLicenceCode);
		//执行分词函数声明
		public String NLPIR_ParagraphProcess(String sSrc,int bPOSTagged);
		//提取关键词函数声明
		public String NLPIR_GetKeyWords(String sLine,int nMaxKeyLimit,boolean bWeightOut);
		//退出函数声明
		public void NLPIR_Exit();
	}
	public static void main(String[] args) throws Exception {
		String argu="";
		String system_charset="GBK";
		int charset_type=1;
		int init_flag=CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset),charset_type,"0".getBytes(system_charset));
		if(init_flag==0){
			System.err.println("初始化失败！");
		}
		String sInput="武汉华中师范大学院长何婷婷2014年大赛下设第九届“挑战杯”大学生创业计划竞赛、创业实践挑战赛、公益创业赛等3项主体赛事，以及MBA、移动互联网创业等2项专项赛。原有的“挑战杯”大学生创业计划竞赛将作为2014年“创青春”全国大学生创业大赛中的一项主体赛事继续开展。";
		String nativeBytes=null;
		nativeBytes=CLibrary.Instance.NLPIR_ParagraphProcess(sInput,3);
		String nativeByte=null;
		nativeByte=CLibrary.Instance.NLPIR_GetKeyWords(sInput,4,false);
		System.out.println(nativeBytes);
		System.out.println(nativeByte);
		CLibrary.Instance.NLPIR_Exit();
	}

}
