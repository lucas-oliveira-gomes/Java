package br.com.lucas.bouncycastle.signatures.extensions;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public class ICPBrasilSignaturePolicyOIDs {
	private static final ASN1ObjectIdentifier icpBrasilSignaturePolicyOid = new ASN1ObjectIdentifier("2.16.76.1.7.1");

	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * CMS, v2.2<br/>
	 * OID: 2.16.76.1.7.1.1.2.2.
	 */
	public static final ASN1ObjectIdentifier PA_AD_RB_v2_2 = icpBrasilSignaturePolicyOid.branch("1.2.2");
	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * CMS, v2.3<br/>
	 * OID: 2.16.76.1.7.1.1.2.3
	 */
	public static final ASN1ObjectIdentifier PA_AD_RB_v2_3 = icpBrasilSignaturePolicyOid.branch("1.2.3");

	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * PDF, v1.0<br/>
	 * OID: 2.16.76.1.7.1.11.1
	 */
	public static final ASN1ObjectIdentifier PA_PAdES_AD_RB_v1_0 = icpBrasilSignaturePolicyOid.branch("11.1");
	/**
	 * POLITICA ICP-BRASIL PARA ASSINATURA DIGITAL COM REFERENCIA BASICA NO FORMATO
	 * PDF, v1.1<br/>
	 * OID: 2.16.76.1.7.1.11.1.1
	 */
	public static final ASN1ObjectIdentifier PA_PAdES_AD_RB_v1_1 = icpBrasilSignaturePolicyOid.branch("11.1.1");
}
