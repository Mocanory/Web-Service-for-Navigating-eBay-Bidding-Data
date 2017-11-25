package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

public class MyParser {

    static DocumentBuilder builder;
    static class MyErrorHandler implements ErrorHandler {
        public void warning(SAXParseException exception)
        throws SAXException {fatalError(exception);}
        public void error(SAXParseException exception)
        throws SAXException {fatalError(exception);}
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
    }
    static {
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        }
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
    }

    static Item parseXML(String xml){
        Document doc = null;
        try { doc = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));}
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("  (not supposed to happen with supplied XML data)");
            e.printStackTrace();
            System.exit(3);
        }
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xml);
        return parseItem(doc.getDocumentElement());
    }

    static Item parseItem(Element item){
        Item myItem = new Item();
        try {
            myItem.setID(item.getAttribute("ItemID"));
            myItem.setName(getElementTextByTagNameNR(item, "Name"));
            myItem.setCurrently(getElementTextByTagNameNR(item, "Currently"));
            myItem.setBuyPrice(getElementTextByTagNameNR(item, "Buy_Price"));
            myItem.setFirstBid(getElementTextByTagNameNR(item, "First_Bid"));
            myItem.setNumberOfBids(getElementTextByTagNameNR(item, "Number_of_Bids"));
            myItem.setCountry(getElementTextByTagNameNR(item, "Country"));
            myItem.setStarted(getElementTextByTagNameNR(item, "Started"));
            myItem.setEnds(getElementTextByTagNameNR(item, "Ends"));
            myItem.setDescription(getElementTextByTagNameNR(item, "Description"));
            // Category Location Seller Bids
            myItem.setCategories(parseCategory(getElementsByTagNameNR(item, "Category")));
            myItem.setLocation(parseLocation(getElementByTagNameNR(item, "Location")));
            myItem.setSeller(parseSeller(getElementByTagNameNR(item, "Seller")));
            myItem.setBids(parseBids(getElementByTagNameNR(item, "Bids")));
        } catch (Exception err) {
            err.printStackTrace();
            System.exit(7);
        }
        return myItem;
    }

    static String[] parseCategory(Element category_list[]) throws Exception{
        List<String> categories = new ArrayList<String>();
        for(Element c : category_list)
          categories.add(getElementText(c));
        return (String[])categories.toArray(new String[categories.size()]);
    }

    static Location parseLocation(Element location) throws Exception{
        String LocationTXT = getElementText(location);
        String Latitude = location.getAttribute("Latitude");
        String Longitude = location.getAttribute("Longitude");
        return new Location(LocationTXT, Latitude, Longitude);
    }

    static User parseSeller(Element seller) throws Exception{
        String SellerID = seller.getAttribute("UserID");
        String Rating = seller.getAttribute("Rating");
        return new User(SellerID, Rating);
    }

    static Bid[] parseBids(Element bidsEle) throws Exception{
        List<Bid> bids = new ArrayList<Bid>();
        Element[] bid_list = getElementsByTagNameNR(bidsEle, "Bid");
        for(Element b : bid_list) {
            User Bidder = parseBidder(getElementByTagNameNR(b, "Bidder"));
            String Time = getElementTextByTagNameNR(b, "Time");
            String Amount = getElementTextByTagNameNR(b, "Amount");
            bids.add(new Bid(Bidder, Time, Amount));
        }
        return (Bid[])bids.toArray(new Bid[bids.size()]);
    }

    static User parseBidder(Element bidder) throws Exception{
        String BidderID = bidder.getAttribute("UserID");
        String Rating = bidder.getAttribute("Rating");
        String Location = getElementTextByTagNameNR(bidder, "Location");
        String Country = getElementTextByTagNameNR(bidder, "Country");
        return new User(BidderID, Rating, Location, Country);
    }

    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                elements.add( (Element)child );
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }

    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }

    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }

    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }

    /*
    public static void main(String[] args){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Item ItemID=\"1044880223\"><Name>Dept. 56 Farmer's Market N Eng Village NIB</Name><Category>Collectibles</Category><Category>Decorative &amp; Holiday</Category><Category>Decorative by Brand</Category><Category>Dept 56</Category><Category>New England Village</Category><Currently>$26.00</Currently><First_Bid>$19.99</First_Bid><Number_of_Bids>3</Number_of_Bids><Bids><Bid><Bidder Rating=\"697\" UserID=\"doe12\"><Location>MOTOR CITY USA</Location><Country>USA</Country></Bidder><Time>Dec-07-01 21:30:37</Time><Amount>$21.99</Amount></Bid><Bid><Bidder Rating=\"963\" UserID=\"fancypantscass\"><Location>Seattle, WA.</Location><Country>USA</Country></Bidder><Time>Dec-10-01 05:30:37</Time><Amount>$24.00</Amount></Bid><Bid><Bidder Rating=\"674\" UserID=\"cpcoll@sprintmail.com\"><Location>Richmond</Location><Country>USA</Country></Bidder><Time>Dec-10-01 05:30:37</Time><Amount>$2.00</Amount></Bid></Bids><Location Latitude=\"39.056521\" Longitude=\"-74.816619\">Southern NJ</Location><Country>USA</Country><Started>Dec-06-01 17:30:37</Started><Ends>Dec-13-01 17:30:37</Ends><Seller Rating=\"3524\" UserID=\"newadditions\"/><Description>Department 56 Farmer's Market New England Village NIB This is \"Farmer's Market\", by Department 56, item #56637, as part of the New England Village. This two-piece set was introduced in 1998, with an original retail: $55.00 (US) / $88.00(Canada). Per the sleeve \"Bright orange pumpkins are this farmer's pride. Because of its countless seeds the pumpkin is regarded as symbol of fertility.\" This has never been displayed and is in mint condition in its original box and sleeve. From a smoke-free &amp; pet-free home. Winning Bidder prepays including actual US Priority postage which for this item will be $5.20, plus insurance. We DO NOT charge any \"handling\" fees. We gladly combine won items to save on shipping, so please feel to bid on our other items. NO OUT OF U.S. BIDDERS! Our guarantee: Every item/lot we list on eBay is guaranteed to be as represented. If the item is not as represented, item must be returned in original condition and then refunds will be issued after our receipt of the item, less eBay fees and shipping, unless otherwise agreed. NOTE: We use the automated PAYPAL end of auction notification service. If you do not receive the message, or prefer to mail your payment, please contact us After the auction ends with the item title, your mailing address, and the item #. Thank you! cosmetics, sports nutrition, and an assortment of 70s/80s collectibles are just a few of our ongoing listings fare! We ARE Able to accept credit card payment from: Paypal.com We accept: checks, money orders and cash. We do not accept billpoint. Please check out the other auctions we currently have running on eBay. Please feel free to e-mail us with questions. \"New\" Additions family Items and Collectibles you can Trust</Description></Item>";

        Item item = parseXML(xml);
        item.orderBids();
        Bid bids[] = item.getBids();
        for(int i = 0; i<bids.length; ++i){
          User seller = bids[i].getBidder();
          System.out.println(seller.getID());
          System.out.println(seller.getRating());
          System.out.println(seller.getLocation());
          System.out.println(seller.getCountry());
          System.out.println(bids[i].getTime());
          System.out.println(bids[i].getAmount());
          System.out.println(bids[i].getMoney());
          System.out.println();
      }
    }
    */
}
