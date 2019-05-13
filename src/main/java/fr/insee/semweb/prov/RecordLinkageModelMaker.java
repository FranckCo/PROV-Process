package fr.insee.semweb.prov;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.insee.rdf.vocabularies.COOS;
import fr.insee.rdf.vocabularies.PROV;

public class RecordLinkageModelMaker {

	public static Logger logger = LogManager.getLogger(RecordLinkageModelMaker.class);

	/**
	 * Reads the code list of equipment types in the DBF file into a Jena model.
	 * 
	 * @return A Jena <code>Model</code> containing the code list as a SKOS concept scheme.
	 */
	public static Model makeRecordLinkageModel() {

		Model recordLinkageModel = ModelFactory.createDefaultModel();
		recordLinkageModel.setNsPrefix("rdfs", RDFS.getURI());
		recordLinkageModel.setNsPrefix("prov", PROV.getURI());
		recordLinkageModel.setNsPrefix("coos", COOS.getURI());
		recordLinkageModel.setNsPrefix("ex", Configuration.RL_EXAMPLE_BASE_URI);
		recordLinkageModel.setNsPrefix("ex-act", Configuration.RL_EXAMPLE_ACTIVITY_BASE_URI);
		recordLinkageModel.setNsPrefix("ex-prod", Configuration.RL_EXAMPLE_PRODUCT_BASE_URI);

		// Simple model
		// Create input data sets A and B and the corresponding collection
		String entityLabel = "Input DB A";
		Resource inputAResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), COOS.StatisticalDataset);
		inputAResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		entityLabel = "Input DB B";
		Resource inputBResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), COOS.StatisticalDataset);
		inputBResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		entityLabel = "Input datasets";
		Resource inputCollectionResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), PROV.Collection);
		inputCollectionResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		inputCollectionResource.addProperty(PROV.hadMember, inputAResource);
		inputCollectionResource.addProperty(PROV.hadMember, inputBResource);

		// Create output data sets and the corresponding collection
		entityLabel = "Match";
		Resource outputMatchResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), COOS.StatisticalDataset);
		outputMatchResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		entityLabel = "Possible match";
		Resource outputPossibleMatchResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), COOS.StatisticalDataset);
		outputPossibleMatchResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		entityLabel = "Unmatch";
		Resource outputUnmatchResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), COOS.StatisticalDataset);
		outputUnmatchResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		entityLabel = "Output datasets";
		Resource outputCollectionResource = recordLinkageModel.createResource(Configuration.uneceProductURI(entityLabel), PROV.Collection);
		outputCollectionResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(entityLabel, "en"));
		outputCollectionResource.addProperty(PROV.hadMember, outputMatchResource);
		outputCollectionResource.addProperty(PROV.hadMember, outputPossibleMatchResource);
		outputCollectionResource.addProperty(PROV.hadMember, outputUnmatchResource);

		// Create the record linkage activity  class (placed for now in the COOS ontology namespace) and specific instance
		Resource recordLinkageActivityClass = recordLinkageModel.createResource(COOS.getURI() + "RecordLinkageActivity");
		recordLinkageActivityClass.addProperty(RDFS.subClassOf, COOS.StatisticalActivity);
		recordLinkageActivityClass.addProperty(RDFS.label, recordLinkageModel.createLiteral("Record Linkage", "en"));
		String recordLinkageActivityLabel = "Match input datasets";
		Resource recordLinkageResource = recordLinkageModel.createResource(Configuration.uneceActivityURI(recordLinkageActivityLabel), recordLinkageActivityClass);
		recordLinkageResource.addProperty(RDFS.label, recordLinkageModel.createLiteral(recordLinkageActivityLabel, "en"));
		recordLinkageResource.addProperty(PROV.used, inputCollectionResource);
		outputCollectionResource.addProperty(PROV.wasGeneratedBy, recordLinkageResource);
		outputCollectionResource.addProperty(PROV.wasDerivedFrom, inputCollectionResource);

		// Qualified model for record linkage derivation
		// Specialized 'DataSetDerivation class (placed for now in the COOS ontology namespace) and specific instance
		Resource dataSetDerivationClass = recordLinkageModel.createResource(COOS.getURI() + "DataSetDerivation"); // We could also create RecordLinkageDataSetDerivation
		dataSetDerivationClass.addProperty(RDFS.subClassOf, PROV.Derivation);
		dataSetDerivationClass.addProperty(RDFS.label, recordLinkageModel.createLiteral("Data Set Derivation", "en"));
		Resource recordLinkageDerivation = recordLinkageModel.createResource(Configuration.RL_EXAMPLE_BASE_URI + "deriveOutputs", dataSetDerivationClass);
		recordLinkageDerivation.addProperty(RDFS.label, recordLinkageModel.createLiteral("Derive outputs", "en"));
		outputCollectionResource.addProperty(PROV.qualifiedDerivation, recordLinkageDerivation);
		recordLinkageDerivation.addProperty(PROV.entity, inputCollectionResource);
		recordLinkageDerivation.addProperty(PROV.hadActivity, recordLinkageResource);

		// Addition of RELAIS and Istat agents
		Resource relaisSoftwareResource = recordLinkageModel.createResource(Configuration.RELAIS_URI, PROV.SoftwareAgent);
		relaisSoftwareResource.addProperty(RDFS.label, recordLinkageModel.createLiteral("RELAIS", "en"));
		recordLinkageResource.addProperty(PROV.wasAssociatedWith, relaisSoftwareResource);
		Resource istatResource = recordLinkageModel.createResource(Configuration.ISTAT_URI, COOS.StatisticalOrganization);
		istatResource.addProperty(RDFS.label, recordLinkageModel.createLiteral("Istat", "en"));
		relaisSoftwareResource.addProperty(PROV.actedOnBehalfOf, istatResource);
		

		return recordLinkageModel;
	}

}