package com.santossingh.capstoneproject.amazon.services;

import android.util.Xml;

import com.santossingh.capstoneproject.amazon.models.AmazonBook;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("HardCodedStringLiteral")
public class MyXmlPullParser {
    private static final String BASE_TAG = "ItemSearchResponse";
    private static final String PARENT_TAG = "Items";
    private static final String TARGET_TAG = "Item";
    private static final String ASIN = "ASIN";
    private static final String DETAIL = "DetailPageURL";
    private static final String IMAGE_PATH = "MediumImage";
    private static final String IMAGE = "URL";
    private static final String ATTRIBUTES_PATH = "ItemAttributes";
    private static final String AUTHOR = "Author";
    private static final String Price_PATH = "ListPrice";
    private static final String PRICE = "FormattedPrice";
    private static final String PUB_DATE = "PublicationDate";
    private static final String TITLE = "Title";
    private static final String REVIEW_PATH = "CustomerReviews";
    private static final String REVIEWS = "IFrameURL";
    private static final String DES_PATH = "EditorialReviews";
    private static final String DES_PATH2 = "EditorialReview";
    private static final String DESCRIPTION = "Content";

    private final String ns = null;

    String BookAuthor = "0", BookYear = "0", BookPrice = "0", BookTitle = "";

    public List<AmazonBook> parse(InputStream in) throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<AmazonBook> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<AmazonBook> booksList = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, BASE_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(PARENT_TAG)) {
                booksList = readInFeed(parser);
            } else {
                skip(parser);
            }
        }
        return booksList;
    }

    private List<AmazonBook> readInFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<AmazonBook> booksList = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, PARENT_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TARGET_TAG)) {
                booksList.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return booksList;
    }

    private AmazonBook readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, TARGET_TAG);
        AmazonBook book = new AmazonBook();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(ASIN)) {
                book.setAsin(readTAG(ASIN, parser));

            } else if (name.equals(DETAIL)) {
                book.setDetailURL(readTAG(DETAIL, parser));

            } else if (name.equals(IMAGE_PATH)) {
                book.setImage(readSUB_TAG(IMAGE_PATH, IMAGE, parser));

            } else if (name.equals(ATTRIBUTES_PATH)) {
                readAttributes(parser);
                book.setAuthor(BookAuthor);
                book.setPrice(BookPrice);
                book.setPublishedDate(BookYear);
                book.setTitle(BookTitle);

            } else if (name.equals(REVIEW_PATH)) {
                book.setReviews(readSUB_TAG(REVIEW_PATH, REVIEWS, parser));

            } else if (name.equalsIgnoreCase(DES_PATH)) {
                book.setDescription(readNestedTAG1(parser));

            } else {
                skip(parser);
            }
        }

        return book;
    }

    // getting other attributes that into a single tag ---------
    private void readAttributes(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, MyXmlPullParser.ATTRIBUTES_PATH);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case AUTHOR:
                    BookAuthor = readTAG(AUTHOR, parser);
                    break;
                case Price_PATH:
                    BookPrice = readSUB_TAG(Price_PATH, PRICE, parser);
                    break;
                case PUB_DATE:
                    BookYear = readTAG(PUB_DATE, parser);
                    break;
                case TITLE:
                    BookTitle = readTAG(TITLE, parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
    }

    private String readNestedTAG1(XmlPullParser parser) throws IOException, XmlPullParserException {
        String data = "Unavailable";
        parser.require(XmlPullParser.START_TAG, ns, MyXmlPullParser.DES_PATH);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(MyXmlPullParser.DES_PATH2)) {
                data = readSUB_TAG(MyXmlPullParser.DES_PATH2, MyXmlPullParser.DESCRIPTION, parser);
            } else {
                skip(parser);
            }
        }

        return data;
    }

    private String readSUB_TAG(String TAG1, String TAG2, XmlPullParser parser) throws IOException, XmlPullParserException {
        String data = "Not found";
        parser.require(XmlPullParser.START_TAG, ns, TAG1);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG2)) {
                data = readTAG(TAG2, parser);
            } else {
                skip(parser);
            }
        }

        return data;
    }

    private String readTAG(String TAG, XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        String data = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG);
        return data;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "Value Not Available";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
