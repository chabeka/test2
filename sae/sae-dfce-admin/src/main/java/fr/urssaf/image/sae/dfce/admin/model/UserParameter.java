package fr.urssaf.image.sae.dfce.admin.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Classe concrète contenant les caractéristiques de l’utilisateur mit à
 * disposition pour se connecter à la base de stockage. <BR />
 * Elle contient :
 * <ul>
 * <li>
 * login : Représente le login de l’utilisateur</li>
 * <li>
 * password : Représente le mot de passe de l’utilisateur</li>
 * </ul>
 */
public class UserParameter {

	private String login;
	private String password;

	/**
	 * Retourne le login de l’utilisateur
	 * 
	 * @return L'identifiant de login
	 */
	public final String getLogin() {
		return login;
	}

	/**
	 * Initialise le login de l’utilisateur
	 * 
	 * @param login
	 *            : Le login de l'utilisateur
	 */
	public final void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * Retourne le mot de passe de l’utilisateur
	 * 
	 * @return Le mot de passe
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * Initialise le mot de passe de l’utilisateur
	 * 
	 * @param password
	 *            : Le mot de passe de l'utilisateur
	 */
	public final void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * Constructeur
	 * 
	 * @param login
	 *            : Le login de l'utilisateur
	 * @param password
	 *            : Le mot de passe de l'utilisateur
	 */
	public UserParameter(final String login, final String password) {
		this.login = login;
		this.password = password;
	}

	/**
	 * Constructeur
	 * 
	 */
	public UserParameter() {
		// ici on ne fait rien
	}

	/**
	 * {@inheritDoc}
	 * */
	@Override
	public final String toString() {
		return new ToStringBuilder(this).append("login", login)
				.append("password", password).toString();
	}
}
