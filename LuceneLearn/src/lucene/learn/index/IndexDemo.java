package lucene.learn.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
/**
 * 建立索引
 */
public class IndexDemo {
	String filePath="data/document.txt";
	String indexPath="data/index";
	public static void main(String[] args) throws Exception {
		IndexDemo luIndexer=new IndexDemo();
		luIndexer.buildIndex();
	}
	public void buildIndex() throws Exception{
		//索引的保存路径
		//Directory dir=FSDirectory.open(new File(indexPath).toPath());
		Directory dir=FSDirectory.open(Paths.get(indexPath));
		//Directory dir=MMapDirectory.open(new File(indexPath).toPath());
		//Directory dir=new RAMDirectory();  //在内存中建立索引
		//IndexWriterConfig配置IndexWriter的一些属性
		IndexWriterConfig iwc=new IndexWriterConfig(new StandardAnalyzer());
		iwc.setRAMBufferSizeMB(IndexWriterConfig.DEFAULT_RAM_BUFFER_SIZE_MB);  //设置缓存大小
		iwc.setMaxBufferedDocs(IndexWriterConfig.DEFAULT_MAX_BUFFERED_DOCS); //设置最大缓存的文档数，超过时会flush
		//创建IndexWriter对象
		IndexWriter iw=new IndexWriter(dir,iwc);
		BufferedReader br=new BufferedReader(new FileReader(filePath));
		String line=null;
		
		//设置索引Field的类型
		FieldType type=new FieldType();
		type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS); //索引文档和FREQS
		type.setStored(true); //存储
		type.setStoreTermVectors(true);
		type.setTokenized(true); //分词
		
		while((line=br.readLine())!=null){
			//创建Document
			Document doc=new Document();
			doc.add(new Field("content",line,type));
			//添加Docuemnt到索引
			iw.addDocument(doc);
		}
		//把内存中索引flush到磁盘
		iw.flush();
		//提交所有的change
		iw.commit();
		//关闭IndexWriter
		iw.close();
	}

}
