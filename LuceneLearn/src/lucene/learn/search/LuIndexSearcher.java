package lucene.learn.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

/**
 *IndexSearcher的用法 
 */
public class LuIndexSearcher {

	/**
	 * 最基本的IndexSearcher
	 * @param indexPath 索引的路径
	 * @return
	 * @throws Exception 
	 */
	public IndexSearcher buildBaseIndexSearcher(String indexPath) throws Exception{
		//IndexReader是一个抽象类，可以使用它的子类DirectoryReader的open方法实例化
		IndexReader ir=DirectoryReader.open(FSDirectory.open(new File(indexPath).toPath()));
		//创建IndexSearcher类
		IndexSearcher is=new IndexSearcher(ir);
		return is;
	}
}
