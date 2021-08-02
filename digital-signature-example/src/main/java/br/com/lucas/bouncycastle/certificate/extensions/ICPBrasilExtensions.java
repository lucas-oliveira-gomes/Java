package br.com.lucas.bouncycastle.certificate.extensions;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/**
 * Para mais informações, consulte o DOC-ICP-04 no site do ITI
 * 
 * @author Lucas
 */
public final class ICPBrasilExtensions {
	private static final ASN1ObjectIdentifier ICPExtension = new ASN1ObjectIdentifier("2.16.76.1");

	public static final ASN1ObjectIdentifier nascCpfNisRgTitular = ICPExtension.branch("3.1").intern();
	public static final ASN1ObjectIdentifier cadastroEspecificoDoINSSTitular = ICPExtension.branch("3.6").intern();
	public static final ASN1ObjectIdentifier titutoDeEleitor = ICPExtension.branch("3.5").intern();
	public static final ASN1ObjectIdentifier nascCpfNisRgResponsavel = ICPExtension.branch("3.4").intern();
	public static final ASN1ObjectIdentifier nomeDoResponsavelPeloCertificado = ICPExtension.branch("3.2").intern();
	public static final ASN1ObjectIdentifier cnpj = ICPExtension.branch("3.3").intern();
	public static final ASN1ObjectIdentifier cadastroEspecificoDoINSSPessoaJuridica = ICPExtension.branch("3.7")
			.intern();

}
