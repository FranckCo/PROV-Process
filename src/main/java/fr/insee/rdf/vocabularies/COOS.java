package fr.insee.rdf.vocabularies;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.SKOS;

/**
 * Vocabulary definition for the <a href="https://github.com/linked-statistics/COOS/">COOS Ontology</a>.
 */
public class COOS {
	/**
	 * The RDF model that holds the COOS entities
	 */
	private static OntModel model = ModelFactory.createOntologyModel();
	/**
	 * The namespace of the COOS vocabulary as a string
	 */
	public static final String uri = "http://id.unece.org/def/coos#";
	/**
	 * Returns the namespace of the COOS vocabulary as a string
	 * @return the namespace of the COOS vocabulary
	 */
	public static String getURI() {
		return uri;
	}
	/**
	 * The namespace of the COOS vocabulary
	 */
	public static final Resource NAMESPACE = model.createResource(uri);

	/* ##########################################################
	 * Defines COOS Classes
	   ########################################################## */

	public static final OntClass Activity = model.createClass(uri + "Activity");
	static {
		Activity.setSuperClass(SKOS.Concept);
		Activity.setSuperClass(PROV.Activity);
	}
	public static final OntClass ActivityArea = model.createClass(uri + "ActivityArea");
	static {
		ActivityArea.setSuperClass(SKOS.Concept);
	}//StatisticalActivity
	public static final OntClass StatisticalActivity = model.createClass(uri + "StatisticalActivity");
	static {
		StatisticalActivity.setSuperClass(Activity);
	}

	public static final OntClass StatisticalDataset = model.createClass(uri + "StatisticalDataset");
	public static final OntClass StatisticalOrganization = model.createClass(uri + "StatisticalOrganization");
}