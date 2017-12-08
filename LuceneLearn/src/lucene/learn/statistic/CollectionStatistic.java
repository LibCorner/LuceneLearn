package lucene.learn.statistic;

import lucene.learn.search.LuIndexSearcher;

import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.IndexSearcher;
/**
 *使用CollectionStatistic统计文档和词项信息 ，
 *使用IndexSearcher对象获得CollectionStatistic实例,
 *可以统计一个Field中文档的数量和单词的数量
 */
public class CollectionStatistic {
	String indexPath="data/index";
	public static void main(String[] args) throws Exception {
		CollectionStatistic ds=new CollectionStatistic();
		ds.docStatistic();
	}
	
	public void docStatistic() throws Exception{
		//创建IndexSearcher
		IndexSearcher is=new LuIndexSearcher().buildBaseIndexSearcher(indexPath);
		//返回content域的collection statistics
		CollectionStatistics cs=is.collectionStatistics("content");
		//Field名字
		String name=cs.field();
		System.out.println("Field:"+name);
		//统计索引中文档的数量,必须有一个词在该域中的文档
		long docNum=cs.docCount();
		long totalDocNum=cs.maxDoc(); //统计所有文档的数量，不需要有词包含在该Field中
		System.out.println("docNum"+docNum+"\tmaxDox:"+totalDocNum);
		//returns the total number of postings for this field
		long docFreq=cs.sumDocFreq();
		System.out.println("docFreq:"+docFreq);
		//统计该域的单词的频次
		long termFreq=cs.sumTotalTermFreq();
		System.out.println("TermFreq:"+termFreq);
		
	}

}
