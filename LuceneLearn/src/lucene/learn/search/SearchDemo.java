package lucene.learn.search;

import java.io.File;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.vectorhighlight.FieldQuery.QueryPhraseMap;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;

/**
 *检索 
 */
public class SearchDemo {
	String indexPath="data/index";
	public static void main(String[] args) throws Exception {
		SearchDemo luSearcher=new SearchDemo();
		luSearcher.search();
	}
	public void search() throws Exception{
		//IndexReader是一个抽象类，可以使用它的子类DirectoryReader的open方法实例化
		IndexReader ir=DirectoryReader.open(FSDirectory.open(new File(indexPath).toPath()));
		//创建IndexSearcher类
		IndexSearcher is=new IndexSearcher(ir);
		//创建Query实例
		TermQuery query=new TermQuery(new Term("content","知"));
		//Query query=new QueryBuilder(new StandardAnalyzer()).createBooleanQuery("content", "新");
		//检索前n个相关的文档,返回的TopDocs类包含hit文档的总数totalHits，和一个ScoreDoc数组。ScoreDoc对象有该文档的id(doc)和得分score
		TopDocs tds=is.search(query, 2);  //IndexSearch对象中保存了检索到的文档
		System.out.println("结果数："+tds.totalHits);
		for(ScoreDoc sd: tds.scoreDocs){
			System.out.println("文档ID:"+sd.doc+"\n文档得分："+sd.score);
			//输出得分是如何计算的
			System.out.println(is.explain(query, sd.doc));
			//根据文档id从IndexSeacher中获得该文档的Document对象
			Document doc=is.doc(sd.doc);
			//输出文档内容
			System.out.println(doc.get("content"));
		}
	}
}
