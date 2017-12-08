package com.ccnu.seg;

import java.util.ArrayList;
import java.util.List;


public class NLPIRSegmenter {
	public NLPIRSegmenter() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void init() throws Exception{
		String argu="";
		String system_charset="utf-8";
		int charset_type=1;
		int init_flag=CLibrary.Instance.NLPIR_Init(argu.getBytes(system_charset),charset_type,"0".getBytes(system_charset));
		if(init_flag==0){
			System.err.println("初始化失败！");
		}
		String userDict="source/userdict.txt";
		int uCount=CLibrary.Instance.NLPIR_ImportUserDict(userDict, true);
	}
	
	public List<Lexeme> getLexeme(String sInput){
		List<Lexeme> results=new ArrayList<Lexeme>();
		if(sInput.trim().equals(""))
			return results;
		String[][] wordTags=getWord_TagsArray(sInput);
		int offset=0;
		for(int i=0;i<wordTags.length;i++){
			String word=wordTags[i][0];
			int begin=0; //???
			int length=word.length();
			String lexemeTag=wordTags[i][1];
			Lexeme l=new Lexeme(offset, begin, length, lexemeTag);
			l.setLexemeText(word);
			results.add(l);
			offset+=length;
		}
		return results;		
	}
	public String[] getWord2Array(String sInput){
		String words=getWords(sInput);
		if(words!=null){
		  String[] wt=words.split(" ");
		  return wt;
		}
		return null;
	}
	public String[][] getWord_TagsArray(String sInput){
		String word_tags=getWord_Tags(sInput);
		if(word_tags!=null){
			  String[] wt=word_tags.split(" ");
			  String[][] wts=new String[wt.length][];
			  int i=0;
			  for(String w:wt){
				  String[] res=w.split("/");
				  wts[i]=res;
				  i++;
			  }
			  return wts;
			}
			return null;
	}
	public String[] getKeyWordsArray(String sInput,int keyNum){
		String key_words=getKeyWords(sInput, keyNum);
		if(key_words!=null)
		  return key_words.split("#");
		else return null;
	}
	
	/**
	 * 词和标注
	 * @param sInput
	 * @return
	 */
	public String getWord_Tags(String sInput){
		return CLibrary.Instance.NLPIR_ParagraphProcess(sInput,1);
	}
	/**
	 * 关键词
	 * @param sInput
	 * @param keyNum
	 * @return
	 */
	public String getKeyWords(String sInput,int keyNum){
		return CLibrary.Instance.NLPIR_GetKeyWords(sInput,keyNum,false);
	}

	/**
	 * 词
	 * @param sInput
	 * @return
	 */
	public String getWords(String sInput){
		return CLibrary.Instance.NLPIR_ParagraphProcess(sInput,0);
	}
	
	public void exit(){
		CLibrary.Instance.NLPIR_Exit();
	}

}
