package lucene.learn.similarities;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
/**
 *修改lucene的评分方法
 */
public class ChangeScore {
	
	public static void main(String[] args) throws Exception {
		ChangeScore its=new ChangeScore();
		IndexWriter iw=its.buildIndexWriter("data/index");
		
	}

	/**
	 * 在索引时可以通过IndexWriterConfig.setSimiarity()修改评分方法
	 * 索引和检索的评分方法应该一致
	 * @param indexPath
	 * @return
	 * @throws Exception
	 */
	public IndexWriter buildIndexWriter(String indexPath) throws Exception{
		//索引路径
		Directory dir=MMapDirectory.open(new File(indexPath).toPath());
		//IndexWriter配置
		IndexWriterConfig iwc=new IndexWriterConfig(new StandardAnalyzer());
		iwc.setSimilarity(new BM25Similarity());  //设置要使用的评分方法，Similarity的子类
		//构建IndexWriter
		IndexWriter iw=new IndexWriter(dir,iwc);
		return iw;
	}
	/**
	 * 在检索的时候通过IndexSearcher.setSimilarity()修改评分方法
	 * 索引和检索的评分方法应该一致
	 * @param indexPath
	 * @return
	 * @throws Exception
	 */
	public IndexSearcher buildIndexSearcher(String indexPath) throws Exception{
		//创建indexReader
		IndexReader dr=DirectoryReader.open(FSDirectory.open(new File(indexPath).toPath()));
		//IndexSearcher
		IndexSearcher is=new IndexSearcher(dr);
		is.setSimilarity(new BM25Similarity()); //设置评分方法，Similarity的子类
		return is;
	}
}
