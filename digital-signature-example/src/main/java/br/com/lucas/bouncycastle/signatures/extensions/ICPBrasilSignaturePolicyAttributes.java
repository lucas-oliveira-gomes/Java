package br.com.lucas.bouncycastle.signatures.extensions;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.esf.OtherHashAlgAndValue;
import org.bouncycastle.asn1.esf.SigPolicyQualifierInfo;
import org.bouncycastle.asn1.esf.SigPolicyQualifiers;
import org.bouncycastle.asn1.esf.SignaturePolicyId;
import org.bouncycastle.asn1.esf.SignaturePolicyIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class ICPBrasilSignaturePolicyAttributes {
	public static final String PA_AD_RB_v2_2_SPQ_URI = "http://politicas.icpbrasil.gov.br/PA_AD_RB_v2_2.der";
	public static final String PA_AD_RB_v2_2_Hash = "sdfsdjflskdjflsdkjf";

	public static final String PA_AD_RB_v2_3_SPQ_URI = "http://politicas.icpbrasil.gov.br/PA_AD_RB_v2_3.der";
	public static final String PA_AD_RB_v2_3_Hash = "asjdkhljhfalsdkfjhsdlfhlsdhjf";

	public static final String PA_PAdES_AD_RB_v1_0_SPQ_URI = "http://politicas.icpbrasil.gov.br/PA_PAdES_AD_RB_v1_0.der";
	public static final String PA_PAdES_AD_RB_v1_0_Hash = "lfkjdsvljfsdçfjlfjsdfkljg";

	public static final String PA_PAdES_AD_RB_v1_1_SPQ_URI = "http://politicas.icpbrasil.gov.br/PA_PAdES_AD_RB_v1_1.der";
	public static final String PA_PAdES_AD_RB_v1_1_Hash = "kjfhdsfkshfskjhfksjfh";

	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * CMS, v2.2<br/>
	 * OID: 2.16.76.1.7.1.1.2.2.<br/>
	 * Valid until 02/03/2029 - dd/MM/yyyy<br/>
	 * SPQ_URI: http://politicas.icpbrasil.gov.br/PA_AD_RB_v2_2.der<br/>
	 * Policy Hash: ....
	 */
	public static final Attribute PA_AD_RB_v2_2 = signaturePolicy(ICPBrasilSignaturePolicyOIDs.PA_AD_RB_v2_2,
			PA_AD_RB_v2_2_SPQ_URI, PA_AD_RB_v2_2_Hash);

	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * CMS, v2.3<br/>
	 * OID: 2.16.76.1.7.1.1.2.3.<br/>
	 * Valid until 02/03/2029 - dd/MM/yyyy<br/>
	 * SPQ_URI: http://politicas.icpbrasil.gov.br/PA_AD_RB_v2_2.der<br/>
	 * Policy Hash: ....
	 */
	public static final Attribute PA_AD_RB_v2_3 = signaturePolicy(ICPBrasilSignaturePolicyOIDs.PA_AD_RB_v2_3,
			PA_AD_RB_v2_3_SPQ_URI, PA_AD_RB_v2_3_Hash);

	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * PDF, v1.0<br/>
	 * OID: 2.16.76.1.7.1.11.1<br/>
	 * Valid until 02/03/2029 - dd/MM/yyyy<br/>
	 * SPQ_URI: http://politicas.icpbrasil.gov.br/PA_PAdES_AD_RB_v1_0.der<br/>
	 * Policy Hash: ....
	 */
	public static final Attribute PA_PAdES_AD_RB_v1_0 = signaturePolicy(ICPBrasilSignaturePolicyOIDs.PA_AD_RB_v2_3,
			PA_PAdES_AD_RB_v1_0_SPQ_URI, PA_PAdES_AD_RB_v1_0_Hash);

	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * PDF, v1.1<br/>
	 * OID: 2.16.76.1.7.1.11.1.1<br/>
	 * Valid until 02/03/2029 - dd/MM/yyyy<br/>
	 * SPQ_URI: http://politicas.icpbrasil.gov.br/PA_PAdES_AD_RB_v1_1.der<br/>
	 * Policy Hash: ....
	 */
	public static final Attribute PA_PAdES_AD_RB_v1_1 = signaturePolicy(
			ICPBrasilSignaturePolicyOIDs.PA_PAdES_AD_RB_v1_1, PA_PAdES_AD_RB_v1_1_SPQ_URI, PA_PAdES_AD_RB_v1_1_Hash);

	private static Attribute signaturePolicy(ASN1ObjectIdentifier policyOid, String signaturePolicyQulifierUri,
			String signaturePolicyHash) {
		OtherHashAlgAndValue signaturePolicyHashAndValue = new OtherHashAlgAndValue(
				new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256),
				new DEROctetString(signaturePolicyHash.getBytes()));
		SigPolicyQualifierInfo spqi = new SigPolicyQualifierInfo(PKCSObjectIdentifiers.id_spq_ets_uri,
				new DERIA5String(signaturePolicyQulifierUri));
		SigPolicyQualifiers spqs = new SigPolicyQualifiers(new SigPolicyQualifierInfo[] { spqi });
		SignaturePolicyId signaturePolicyId = new SignaturePolicyId(policyOid, signaturePolicyHashAndValue, spqs);

		SignaturePolicyIdentifier signaturePolicyIdentifier = new SignaturePolicyIdentifier(signaturePolicyId);
		return new Attribute(PKCSObjectIdentifiers.id_aa_ets_sigPolicyId, new DERSet(signaturePolicyIdentifier));
	}
}
