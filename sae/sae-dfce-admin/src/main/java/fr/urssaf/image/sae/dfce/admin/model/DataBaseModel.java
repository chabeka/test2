/**
 * 
 */
package fr.urssaf.image.sae.dfce.admin.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * Cette classe contient le modèle de base SAE suite à la désérialisation du fichier xml [SaeBase.xml].<BR />
 * Elle contient :
 * <ul>
 * <li>base : La base SAE</li>
 * <li>IndexComposites : les index composites</li>
 * <li></li>
 * </ul>
 * 
 */
@XStreamAlias("docuBase")
public class DataBaseModel {
	@XStreamAlias("base")
	private SaeBase base;
	private IndexComposites indexComposites;

	/**
	 * @return the base
	 */
	public final SaeBase getBase() {
		return base;
	}

	/**
	 * @param base
	 *            : une instance de base
	 */
	public final void setBase(final SaeBase base) {
		this.base = base;
	}

	/**
	 * @param indexComposites
	 *            : Les indexes Composites
	 */
	public final void setIndexComposites(final IndexComposites indexComposites) {
		this.indexComposites = indexComposites;
	}

	/**
	 * 
	 * @return une instance de base
	 */
	public final SaeBase getDataBase() {

		return base;
	}

	@Override
	public final String toString() {
		final ToStringBuilder toStringBuilder = new ToStringBuilder(this);
		if (base != null) {
			toStringBuilder.append("base", base.toString());
		}
		if (indexComposites != null) {
			toStringBuilder.append("indexComposites",
					indexComposites.toString());
		}
		return toStringBuilder.toString();
	}

	/**
	 * 
	 * @return Les indexes Composites
	 */
	public final List<String> getIndexComposites() {

		return indexComposites.getIndexComp();
	}
}