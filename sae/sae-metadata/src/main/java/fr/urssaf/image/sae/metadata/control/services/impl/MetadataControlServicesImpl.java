package fr.urssaf.image.sae.metadata.control.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.urssaf.image.sae.bo.model.MetadataError;
import fr.urssaf.image.sae.bo.model.bo.SAEDocument;
import fr.urssaf.image.sae.bo.model.bo.SAEMetadata;
import fr.urssaf.image.sae.bo.model.untyped.UntypedDocument;
import fr.urssaf.image.sae.bo.model.untyped.UntypedMetadata;
import fr.urssaf.image.sae.metadata.control.services.MetadataControlServices;
import fr.urssaf.image.sae.metadata.exceptions.ReferentialException;
import fr.urssaf.image.sae.metadata.messages.MetadataMessageHandler;
import fr.urssaf.image.sae.metadata.referential.model.MetadataReference;
import fr.urssaf.image.sae.metadata.referential.services.MetadataReferenceDAO;
import fr.urssaf.image.sae.metadata.utils.Utils;

/**
 * Classe qui implémente les sevices de l'interface
 * {@link MetadataControlServices}
 * 
 * @author akenore
 * 
 */
@Service
@Qualifier("metadataControlServices")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class MetadataControlServicesImpl implements MetadataControlServices {
	@Autowired
	@Qualifier("ruleFactory")
	private MetadataRuleFactory ruleFactory;
	@Autowired
	@Qualifier("metadataReferenceDAO")
	private MetadataReferenceDAO referenceDAO;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public final List<MetadataError> checkArchivableMetadata(
			final SAEDocument saeDoc) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (SAEMetadata metadata : Utils.nullSafeIterable(saeDoc
				.getMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				if (!ruleFactory.getArchivableRule().isSatisfiedBy(metadata,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.archivage"), metadata
							.getLongCode(), MetadataMessageHandler.getMessage(
							"metadata.not.archivable", metadata.getLongCode())));
				}
			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), metadata
						.getLongCode(),
						MetadataMessageHandler.getMessage(
								"metadata.referentiel.retrieve",
								metadata.getLongCode())));
			}
		}
		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public final List<MetadataError> checkExistingMetadata(
			final UntypedDocument untypedDoc) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (UntypedMetadata metadata : Utils.nullSafeIterable(untypedDoc
				.getUMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				if (!ruleFactory.getExistingRule().isSatisfiedBy(
						metadata.getLongCode(), reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.existing"), metadata
							.getLongCode(), MetadataMessageHandler.getMessage(
							"metadata.not.exist", metadata.getLongCode())));
				}
			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), metadata
						.getLongCode(),
						MetadataMessageHandler.getMessage(
								"metadata.referentiel.retrieve",
								metadata.getLongCode())));
			}

		}
		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public final List<MetadataError> checkMetadataValueTypeAndFormat(
			final UntypedDocument untypedDoc) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (UntypedMetadata metadata : Utils.nullSafeIterable(untypedDoc
				.getUMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				if (!ruleFactory.getValueTypeRule().isSatisfiedBy(metadata,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.type"), metadata
							.getLongCode(), MetadataMessageHandler.getMessage(
							"metadata.bad.type", metadata.getLongCode())));
				}
				if (!ruleFactory.getValueLengthRule().isSatisfiedBy(metadata,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.Length"), metadata
							.getLongCode(), MetadataMessageHandler.getMessage(
							"metadata.length.not.verified",
							metadata.getLongCode(), reference.getLength())));
				}
			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), metadata
						.getLongCode(), MetadataMessageHandler
						.getMessage("metadata.referentiel.retrieve")));
			}

		}
		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "PMD.AvoidInstantiatingObjectsInLoops",
			"PMD.DataflowAnomalyAnalysis" })
	public final List<MetadataError> checkRequiredForArchivalMetadata(
			final SAEDocument saeDoc) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		try {
			final Map<String, MetadataReference> references = referenceDAO
					.getRequiredForArchivalMetadataReferences();
			checkRequired(saeDoc, errors, references);
		} catch (ReferentialException refExcept) {
			errors.add(new MetadataError(MetadataMessageHandler
					.getMessage("metadata.referentiel.error"), null,
					MetadataMessageHandler
							.getMessage("metadata.referentiel.retrieve")));
		}

		return errors;
	}

	/**
	 * Permet de controller la presence des métadonnées obligatoires.
	 * 
	 * @param saeDoc
	 *            : Le document métier
	 * @param errors
	 *            : La liste des erreurs
	 * @param references
	 *            : Les métadonnées du référentiel
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	private void checkRequired(final SAEDocument saeDoc,
			final List<MetadataError> errors,
			final Map<String, MetadataReference> references) {
		for (Entry<String, MetadataReference> metadata : Utils.nullSafeMap(
				references).entrySet()) {
			if (!Utils.isInRequiredList(metadata.getValue(),
					saeDoc.getMetadatas())) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.control.required"), metadata
						.getValue().getLongCode(), MetadataMessageHandler
						.getMessage("metadata.required", metadata.getValue()
								.getLongCode())));
			}
		}
		for (SAEMetadata metadata : Utils.nullSafeIterable(saeDoc
				.getMetadatas())) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				if (!ruleFactory.getRequiredValueRule().isSatisfiedBy(metadata,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.value.required"),
							metadata.getLongCode(), MetadataMessageHandler
									.getMessage("metadata.value.required",
											metadata.getLongCode())));
				}
			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), metadata
						.getLongCode(),
						MetadataMessageHandler.getMessage(
								"metadata.referentiel.retrieve",
								metadata.getLongCode())));
			}
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public final List<MetadataError> checkConsultableMetadata(
			final List<SAEMetadata> metadatas) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (SAEMetadata metadata : Utils.nullSafeIterable(metadatas)) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				if (!ruleFactory.getConsultableRule().isSatisfiedBy(metadata,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.consultable"),
							metadata.getLongCode(), MetadataMessageHandler
									.getMessage("metadata.not.consultable",
											metadata.getLongCode())));
				}
			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), metadata
						.getLongCode(), MetadataMessageHandler
						.getMessage("metadata.referentiel.retrieve")));
			}
		}
		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public final List<MetadataError> checkSearchableMetadata(
			final List<SAEMetadata> metadatas) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (SAEMetadata metadata : Utils.nullSafeIterable(metadatas)) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(metadata.getLongCode());
				if (!ruleFactory.getSearchableRule().isSatisfiedBy(metadata,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.searchable"),
							metadata.getLongCode(), MetadataMessageHandler
									.getMessage("metadata.not.searchable",
											metadata.getLongCode())));
				}

			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), metadata
						.getLongCode(), MetadataMessageHandler
						.getMessage("metadata.referentiel.retrieve")));
			}

		}
		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	public final List<MetadataError> checkDuplicateMetadata(
			final List<UntypedMetadata> metadatas) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (UntypedMetadata metadata : Utils.nullSafeIterable(metadatas)) {
			if (!Utils.hasDuplicate(metadata, metadatas)) {
				final MetadataError error = new MetadataError(
						MetadataMessageHandler
								.getMessage("metadata.control.duplicate"),
						metadata.getLongCode(), MetadataMessageHandler
								.getMessage("metadata.duplicated",
										metadata.getLongCode()));
				if (!Utils.exist(error, errors)) {
					errors.add(error);
				}
			}
		}
		return errors;
	}

	/**
	 * 
	 * @param factory
	 *            : La fabrique des régles.
	 */
	public final void setRuleFactory(final MetadataRuleFactory factory) {
		this.ruleFactory = factory;
	}

	/**
	 * 
	 * @return La fabrique des régles.
	 */
	public final MetadataRuleFactory getRuleFactory() {
		return ruleFactory;
	}

	/**
	 * 
	 * @param referenceDAO
	 *            : Les services de manipulation des métadonnées de référentiel
	 *            des métadonnées.
	 */
	public final void setReferenceDAO(final MetadataReferenceDAO referenceDAO) {
		this.referenceDAO = referenceDAO;
	}

	/**
	 * Les services de manipulation des métadonnées de référentiel des
	 * métadonnées.
	 * 
	 * @return Les services de manipulation des métadonnées de référentiel des
	 *         métadonnées.
	 */
	public final MetadataReferenceDAO getReferenceDAO() {
		return referenceDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "PMD.AvoidInstantiatingObjectsInLoops",
			"PMD.DataflowAnomalyAnalysis" })
	public final List<MetadataError> checkRequiredForStorageMetadata(
			final SAEDocument saeDoc) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		try {
			final Map<String, MetadataReference> references = referenceDAO
					.getRequiredForStorageMetadataReferences();
			checkRequired(saeDoc, errors, references);
		} catch (ReferentialException refExcept) {
			errors.add(new MetadataError(MetadataMessageHandler
					.getMessage("metadata.referentiel.error"), null,
					MetadataMessageHandler
							.getMessage("metadata.referentiel.retrieve")));
		}

		return errors;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "PMD.AvoidInstantiatingObjectsInLoops",
			"PMD.DataflowAnomalyAnalysis" })
	public final List<MetadataError> checkExistingQueryTerms(
			final List<String> longCodes) {
		final List<MetadataError> errors = new ArrayList<MetadataError>();
		for (String codeLong : Utils.nullSafeIterable(longCodes)) {
			try {
				final MetadataReference reference = referenceDAO
						.getByLongCode(codeLong);
				if (!ruleFactory.getExistingRule().isSatisfiedBy(codeLong,
						reference)) {
					errors.add(new MetadataError(MetadataMessageHandler
							.getMessage("metadata.control.existing"), codeLong,
							MetadataMessageHandler.getMessage(
									"metadata.not.exist", codeLong)));
				}
			} catch (ReferentialException refExcept) {
				errors.add(new MetadataError(MetadataMessageHandler
						.getMessage("metadata.referentiel.error"), codeLong,
						MetadataMessageHandler.getMessage(
								"metadata.referentiel.retrieve", codeLong)));
			}

		}
		return errors;
	}

}
