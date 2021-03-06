/*
 * Copyright 2014-2019 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright (C) 2014-2019 Ping Identity Corporation
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */
package com.unboundid.ldap.sdk.unboundidds.controls;



import org.testng.annotations.Test;

import com.unboundid.asn1.ASN1Integer;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.asn1.ASN1Sequence;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSDKTestCase;



/**
 * This class provides a set of test cases for the matching entry count request
 * control.
 */
public final class MatchingEntryCountRequestControlTestCase
       extends LDAPSDKTestCase
{
  /**
   * Tests the behavior of a matching entry count request control created with
   * the default constructor.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDefaultConstructor()
         throws Exception
  {
    MatchingEntryCountRequestControl c = new MatchingEntryCountRequestControl();
    c = new MatchingEntryCountRequestControl(c);

    assertNotNull(c.getOID());
    assertEquals(c.getOID(), "1.3.6.1.4.1.30221.2.5.36");
    assertEquals(c.getOID(),
         MatchingEntryCountRequestControl.MATCHING_ENTRY_COUNT_REQUEST_OID);

    assertTrue(c.isCritical());

    assertNotNull(c.getValue());

    assertEquals(c.getMaxCandidatesToExamine(), 0);

    assertFalse(c.alwaysExamineCandidates());

    assertFalse(c.processSearchIfUnindexed());

    assertFalse(c.skipResolvingExplodedIndexes());

    assertNull(c.getFastShortCircuitThreshold());

    assertNull(c.getSlowShortCircuitThreshold());

    assertFalse(c.includeDebugInfo());

    assertNotNull(c.getControlName());

    assertNotNull(c.toString());
  }



  /**
   * Tests the behavior of a matching entry count request control created with
   * all non-default settings.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testNonDefaultConstructor()
         throws Exception
  {
    MatchingEntryCountRequestControl c =
         new MatchingEntryCountRequestControl(false, 123, true, true, true, 5L,
              20L, true);
    c = new MatchingEntryCountRequestControl(c);

    assertNotNull(c.getOID());
    assertEquals(c.getOID(), "1.3.6.1.4.1.30221.2.5.36");
    assertEquals(c.getOID(),
         MatchingEntryCountRequestControl.MATCHING_ENTRY_COUNT_REQUEST_OID);

    assertFalse(c.isCritical());

    assertNotNull(c.getValue());

    assertEquals(c.getMaxCandidatesToExamine(), 123);

    assertTrue(c.alwaysExamineCandidates());

    assertTrue(c.processSearchIfUnindexed());

    assertTrue(c.skipResolvingExplodedIndexes());

    assertNotNull(c.getFastShortCircuitThreshold());
    assertEquals(c.getFastShortCircuitThreshold().longValue(), 5L);

    assertNotNull(c.getSlowShortCircuitThreshold());
    assertEquals(c.getSlowShortCircuitThreshold().longValue(), 20L);

    assertTrue(c.includeDebugInfo());

    assertNotNull(c.getControlName());

    assertNotNull(c.toString());
  }



  /**
   * Tests the behavior when trying to decode a control that does not have a
   * value.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeMissingValue()
         throws Exception
  {
    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true, null));
  }



  /**
   * Tests the behavior when trying to decode a control whose value is not a
   * sequence.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeValueNotSequence()
         throws Exception
  {
    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true,
              new ASN1OctetString("foo")));
  }



  /**
   * Tests the behavior when trying to decode a control whose value sequence
   * includes an element with an unexpected type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeValueSequenceInvalidElementType()
         throws Exception
  {
    final ASN1Sequence valueSequence = new ASN1Sequence(
         new ASN1OctetString((byte) 0x12, "foo"));

    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true,
              new ASN1OctetString(valueSequence.encode())));
  }



  /**
   * Tests the behavior when trying to decode a control whose value sequence
   * includes a max candidates to examine element with a negative value.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeValueSequenceNegativeMaxCandidatesToExamine()
         throws Exception
  {
    final ASN1Sequence valueSequence = new ASN1Sequence(
         new ASN1Integer((byte) 0x80, -1));

    new MatchingEntryCountRequestControl(
         new Control("1.3.6.1.4.1.30221.2.5.36", true,
              new ASN1OctetString(valueSequence.encode())));
  }
}
