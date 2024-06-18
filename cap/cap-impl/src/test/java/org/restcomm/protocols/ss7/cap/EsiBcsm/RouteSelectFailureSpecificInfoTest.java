/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2012, Telestax Inc and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.restcomm.protocols.ss7.cap.EsiBcsm;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import javolution.xml.XMLObjectReader;
import javolution.xml.XMLObjectWriter;

import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.restcomm.protocols.ss7.cap.EsiBcsm.RouteSelectFailureSpecificInfoImpl;
import org.restcomm.protocols.ss7.cap.isup.CauseCapImpl;
import org.restcomm.protocols.ss7.cap.service.circuitSwitchedCall.primitive.EventSpecificInformationBCSMImpl;
import org.testng.annotations.Test;

/**
 *
 * @author sergey vetyutnev
 *
 */
public class RouteSelectFailureSpecificInfoTest {

    public byte[] getData() {
        return new byte[] { (byte) 162, 4, (byte) 128, 2, (byte) 132, (byte) 144 };
    }

    public byte[] getIntData() {
        return new byte[] { (byte) 132, (byte) 144 };
    }

    @Test(groups = { "functional.decode", "circuitSwitchedCall.primitive" })
    public void testDecode() throws Exception {

        byte[] data = this.getData();
        AsnInputStream ais = new AsnInputStream(data);
        RouteSelectFailureSpecificInfoImpl elem = new RouteSelectFailureSpecificInfoImpl();
        int tag = ais.readTag();
        assertEquals(tag, EventSpecificInformationBCSMImpl._ID_routeSelectFailureSpecificInfo);
        assertEquals(ais.getTagClass(), Tag.CLASS_CONTEXT_SPECIFIC);
        elem.decodeAll(ais);
        assertTrue(Arrays.equals(elem.getFailureCause().getData(), this.getIntData()));
    }

    @Test(groups = { "functional.encode", "circuitSwitchedCall.primitive" })
    public void testEncode() throws Exception {

        CauseCapImpl cause = new CauseCapImpl(this.getIntData());
        RouteSelectFailureSpecificInfoImpl elem = new RouteSelectFailureSpecificInfoImpl(cause);
        AsnOutputStream aos = new AsnOutputStream();
        elem.encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC, EventSpecificInformationBCSMImpl._ID_routeSelectFailureSpecificInfo);
        assertEquals(aos.toByteArray(), this.getData());

    }

    @Test(groups = { "functional.xml.serialize", "circuitSwitchedCall.primitive" })
    public void testXMLSerializaion() throws Exception {

        CauseCapImpl cause = new CauseCapImpl(this.getIntData());
        RouteSelectFailureSpecificInfoImpl original = new RouteSelectFailureSpecificInfoImpl(cause);

        // Writes the area to a file.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLObjectWriter writer = XMLObjectWriter.newInstance(baos);
        // writer.setBinding(binding); // Optional.
        writer.setIndentation("\t"); // Optional (use tabulation for
                                     // indentation).
        writer.write(original, "RouteSelectFailureSpecificInfoImpl", RouteSelectFailureSpecificInfoImpl.class);
        writer.close();

        byte[] rawData = baos.toByteArray();
        String serializedEvent = new String(rawData);

        System.out.println(serializedEvent);

        ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
        XMLObjectReader reader = XMLObjectReader.newInstance(bais);
        RouteSelectFailureSpecificInfoImpl copy = reader.read("RouteSelectFailureSpecificInfoImpl",
                RouteSelectFailureSpecificInfoImpl.class);

        assertEquals(copy.getFailureCause().getCauseIndicators().getLocation(), original.getFailureCause().getCauseIndicators()
                .getLocation());
        assertEquals(copy.getFailureCause().getCauseIndicators().getCauseValue(), original.getFailureCause()
                .getCauseIndicators().getCauseValue());
        assertEquals(copy.getFailureCause().getCauseIndicators().getCodingStandard(), original.getFailureCause()
                .getCauseIndicators().getCodingStandard());

    }
}
