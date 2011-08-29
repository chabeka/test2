package fr.urssaf.image.sae.metadata.control.services;

import java.util.List;

import fr.urssaf.image.sae.bo.model.MetadataError;
import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;

/**
 * Fournit les services de contrôle des métadonnées.
 * 
 * @author akenore
 * 
 */
public interface MetadataControlServices {
	/**
	 * Contrôle que la liste des métadonnées sont autorisées à l'archivables
	 * lors de la capture.
	 * 
	 * @param saeDoc
	 *            : Un objet métier de type {@link SAEDocument}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkArchivableMetadata(final SAEDocument saeDoc);

	/**
	 * Contrôle que la liste des métadonnées existe dans le référentiel.
	 * 
	 * @param untypedDoc
	 *            : Un objet de type {@link UntypedDocument}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkExistingMetadata(final UntypedDocument untypedDoc);

	/**
	 * Contrôle le type ,le format , la taille max de la valeur de chaque
	 * métadonnées.
	 * 
	 * @param untypedDoc
	 *            : Un objet de type {@link UntypedDocument}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkMetadataValueTypeAndFormat(
			final UntypedDocument untypedDoc);

	/**
	 * Contrôle que la liste des métadonnées fournit contient toutes les
	 * métadonnées obligatoire.
	 * 
	 * @param saeDoc
	 *            : Un objet métier de type {@link SAEDocument}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkRequiredMetadata(final SAEDocument saeDoc);

	/**
	 * Contrôle que la liste des métadonnées est autorisée à la consultation.
	 * métadonnées obligatoire.
	 * 
	 * @param metadatas
	 *            : Liste des métadonnées métiers de type {@link SAEMetadata}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkConsultableMetadata(
			final List<SAEMetadata> metadatas);

	/**
	 * Contrôle que la liste des métadonnées est autorisée pour la recherche.
	 * 
	 * 
	 * @param metadatas
	 *            : Liste des métadonnées métiers de type {@link SAEMetadata}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkSearchableMetadata(
			final List<SAEMetadata> metadatas);

	/**
	 * Contrôle que la liste des métadonnées ne contient pas doublon.
	 * 
	 * 
	 * @param metadatas
	 *            : Liste des métadonnées métiers de type {@link SAEMetadata}
	 * @return une liste d’objet de type {@link MetadataError}
	 */
	List<MetadataError> checkDuplicateMetadata(
			final List<UntypedMetadata> metadatas);

}