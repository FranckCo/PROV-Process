package fr.insee.semweb.prov.test;

import java.io.FileWriter;

import org.apache.jena.rdf.model.Model;
import org.junit.Test;

import fr.insee.semweb.prov.RecordLinkageModelMaker;

public class RecordLinkageModelMakerTest {

	@Test
	public void testRecordLinkageModelMaker() throws Exception {

		Model codeList = RecordLinkageModelMaker.makeRecordLinkageModel();
		codeList.write(new FileWriter("src/main/resources/data/record-linkage.ttl"), "TTL");
	}

}
