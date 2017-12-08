package com.ccnu.seg;


public class Lexeme {
	// 词元的起始位移
	private int offset;
	// 词元的相对起始位置
	private int begin;
	// 词元的长度
	private int length;
	// 词元文本
	private String lexemeText;
	// 词元标注
	private String lexemeTag;
	
	public Lexeme(int offset , int begin , int length , String lexemeTag){
		this.offset = offset;
		this.begin = begin;
		if(length < 0){
			throw new IllegalArgumentException("length < 0");
		}
		this.length = length;
		this.lexemeTag = lexemeTag;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getBegin() {
		return begin;
	}
	/**
	 * 获取词元在文本中的起始位置
	 * @return int
	 */
	public int getBeginPosition(){
		return offset + begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	/**
	 * 获取词元在文本中的结束位置
	 * @return int
	 */
	public int getEndPosition(){
		return offset + begin + length;
	}
	
	/**
	 * 获取词元的字符长度
	 * @return int
	 */
	public int getLength(){
		return this.length;
	}	
	
	public void setLength(int length) {
		if(this.length < 0){
			throw new IllegalArgumentException("length < 0");
		}
		this.length = length;
	}
	
	/**
	 * 获取词元的文本内容
	 * @return String
	 */
	public String getLexemeText() {
		if(lexemeText == null){
			return "";
		}
		return lexemeText;
	}

	public void setLexemeText(String lexemeText) {
		if(lexemeText == null){
			this.lexemeText = "";
			this.length = 0;
		}else{
			this.lexemeText = lexemeText;
			this.length = lexemeText.length();
		}
	}

	/**
	 * 获取词元标注
	 * @return int
	 */
	public String getLexemeTag() {
		return lexemeTag;
	}

	public void setLexemeTag(String lexemeTag) {
		this.lexemeTag = lexemeTag;
	}

}
