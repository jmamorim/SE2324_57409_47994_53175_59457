
package net.sf.freecol.common.model;


import java.util.Map;

import net.sf.freecol.common.util.Xml;

import org.w3c.dom.Node;


public final class GoodsType
{
    public static final  String  COPYRIGHT = "Copyright (C) 2003-2006 The FreeCol Team";
    public static final  String  LICENSE = "http://www.gnu.org/licenses/gpl.html";
    public static final  String  REVISION = "$Revision$";

    public final int        index;
    public final String     id;
    public final String     name;
    public final boolean   isFarmed;
    public final boolean   ignoreLimit;
/*
    public boolean improvedByPlowing = false;
    public boolean improvedByRiver = false;
    public boolean improvedByRoad = false;
*/
    public        GoodsType  madeFrom;
    public        GoodsType  makes;
    
    public final GoodsType  storedAs;
    public final boolean   storable;

    public final int initialAmount;
    public final int initialPrice;
    public final int priceDiff;

    // ----------------------------------------------------------- constructors

    public GoodsType(int index) {
        this.index = index;
    }

    // ----------------------------------------------------------- retriveal methods

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public boolean isRawMaterial() {
        return makes != null;
    }

    public boolean isRefined() {
        return madeFrom != null;
    }

    public GoodsType getRawMaterial() {
        return madeFrom;
    }

    public GoodsType getProducedMaterial() {
        return makes;
    }

    public boolean isFarmed() {
        return isFarmed;
    }

    public boolean limitIgnored() {
        return ignoreLimit;
    }

    public boolean isStorable() {
        return storable;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public int getInitialSellPrice() {
        return initialPrice;
    }

    public int getInitialBuyPrice() {
        return initialPrice + priceDiff;
    }

    public int getPriceDifference() {
        return priceDiff;
    }

/*
    public boolean isImprovedByPlowing() {

        return improvedByPlowing;

    }

    public boolean isImprovedByRoad() {

        return improvedByRoad;

    }

    public boolean isImprovedByRiver() {

        return improvedByRiver;

    }
*/
    // ------------------------------------------------------------ API methods

    public void readFromXmlElement( Node xml, Map<String, GoodsType> goodsTypeByRef ) {

        id = Xml.attribute(xml, "name");
        name = Xml.attribute(xml, "name");
        isFarmed = Xml.booleanAttribute(xml, "is-farmed");
        ignoreLimit = Xml.booleanAttribute(xml, "ignore-limit", false);
        storable = Xml.booleanAttribute(xml, "storable", true);

        if (Xml.hasAttribute(xml, "stored-as")) {
            storedAs = goodsTypeByRef.get(Xml.attribute(xml, "stored-as"));
        }

        if (Xml.hasAttribute(xml, "made-from")) {
            String  madeFromRef = Xml.attribute(xml, "made-from");
            GoodsType  rawMaterial = goodsTypeByRef.get(madeFromRef);
            madeFrom = rawMaterial;
            rawMaterial.makes = this;
        }
/*
        if (Xml.hasAttribute(xml, "improved-by-plowing") &&
            Xml.booleanAttribute(xml, "improved-by-plowing")) {
            improvedByPlowing = true;
        }
        if (Xml.hasAttribute(xml, "improved-by-river") &&
            Xml.booleanAttribute(xml, "improved-by-river")) {
            improvedByRiver = true;
        }
        if (Xml.hasAttribute(xml, "improved-by-road") &&
            Xml.booleanAttribute(xml, "improved-by-road")) {
            improvedByRoad = true;
        }
*/
        // Only expected child is 'market'
        Xml.Method method = new Xml.Method() {
            public void invokeOn(Node xml) {
                initialAmount = Xml.intAttribute(xml, "initial-amount");
                initialPrice = Xml.intAttribute(xml, "initial-price");
                priceDiff = Xml.intAttribute(xml, "price-difference");
            }
        };
        Xml.forEachChild(xml, method);
    }

}
