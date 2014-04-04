package com.googlecode.cchlib.xutil.google.googlecontact;

public interface Data {

    static final String[] HEADERS_BASIC = {
        "Name",
        "Given Name",
        "Additional Name",
        "Family Name",
        "Yomi Name",
        "Given Name Yomi",
        "Additional Name Yomi",
        "Family Name Yomi",
        "Name Prefix",
        "Name Suffix",
        "Initials",
        "Nickname",
        "Short Name",
        "Maiden Name",
        "Birthday",
        "Gender",
        "Location",
        "Billing Information",
        "Directory Server",
        "Mileage",
        "Occupation",
        "Hobby",
        "Sensitivity",
        "Priority",
        "Subject",
        "Notes",
        "Group Membership"
    };

    static final String[] ENTRY_BASIC = {
        "(((basicTest))) Last",
        "(((basicTest)))",
        "",
        "Last",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "* My Contacts"
    };


    static final String[] HEADERS_2MAILS = {
        "Name", "E-mail 1 - Type", "E-mail 1 - Value", "E-mail 2 - Type", "E-mail 2 - Value"
    };

    static final String[] ENTRY_2MAILS = {
        "With 2 Mails", "Home", "email2", "* Work", "email1"
    };

    static final String[] HEADERS0 = {
        "Name", "Given Name", "Additional Name", "Family Name", "Yomi Name", "Given Name Yomi", "Additional Name Yomi", "Family Name Yomi", "Name Prefix", "Name Suffix", "Initials", "Nickname", "Short Name", "Maiden Name",
        "Birthday", "Gender", "Location",
        "Billing Information", "Directory Server", "Mileage", "Occupation", "Hobby", "Sensitivity",
        "Priority", "Subject", "Notes",
        "Group Membership",
        "E-mail 1 - Type", "E-mail 1 - Value",
        "E-mail 2 - Type", "E-mail 2 - Value",
        "E-mail 3 - Type", "E-mail 3 - Value",
        "E-mail 4 - Type", "E-mail 4 - Value",
        "E-mail 5 - Type", "E-mail 5 - Value",

        "IM 1 - Type", "IM 1 - Service", "IM 1 - Value",
        "IM 2 - Type", "IM 2 - Service", "IM 2 - Value",

        "Phone 1 - Type", "Phone 1 - Value",
        "Phone 2 - Type", "Phone 2 - Value",
        "Phone 3 - Type", "Phone 3 - Value",
        "Phone 4 - Type", "Phone 4 - Value",
        "Phone 5 - Type", "Phone 5 - Value",
        "Phone 6 - Type", "Phone 6 - Value",
        "Phone 7 - Type", "Phone 7 - Value",
        "Phone 8 - Type", "Phone 8 - Value",

        "Address 1 - Type", "Address 1 - Formatted", "Address 1 - Street", "Address 1 - City", "Address 1 - PO Box", "Address 1 - Region", "Address 1 - Postal Code", "Address 1 - Country", "Address 1 - Extended Address",
        "Address 2 - Type", "Address 2 - Formatted", "Address 2 - Street", "Address 2 - City", "Address 2 - PO Box", "Address 2 - Region", "Address 2 - Postal Code", "Address 2 - Country", "Address 2 - Extended Address",
        "Address 3 - Type", "Address 3 - Formatted", "Address 3 - Street", "Address 3 - City", "Address 3 - PO Box", "Address 3 - Region", "Address 3 - Postal Code", "Address 3 - Country", "Address 3 - Extended Address",
        "Address 4 - Type", "Address 4 - Formatted", "Address 4 - Street", "Address 4 - City", "Address 4 - PO Box", "Address 4 - Region", "Address 4 - Postal Code", "Address 4 - Country", "Address 4 - Extended Address",

        "Organization 1 - Type", "Organization 1 - Name", "Organization 1 - Yomi Name", "Organization 1 - Title", "Organization 1 - Department", "Organization 1 - Symbol", "Organization 1 - Location", "Organization 1 - Job Description",

        "Website 1 - Type", "Website 1 - Value",
        "Website 2 - Type", "Website 2 - Value",
        "Website 3 - Type", "Website 3 - Value",
        "Website 4 - Type", "Website 4 - Value",

        "Custom Field 1 - Type", "Custom Field 1 - Value"
    };

    static final String[] HEADERS1 = {
        "Name",
        "Given Name",
        "Additional Name",
        "Family Name",
        "Yomi Name",
        "Given Name Yomi",
        "Additional Name Yomi",
        "Family Name Yomi",
        "Name Prefix",
        "Name Suffix",
        "Initials",
        "Nickname",
        "Short Name",
        "Maiden Name",

        "Birthday",
        "Gender",
        "Location",
        "Billing Information",
        "Directory Server",
        "Mileage",
        "Occupation",
        "Hobby",
        "Sensitivity",
        "Priority",
        "Subject",
        "Notes",
        "Group Membership",
        "E-mail 1 - Type",
        "E-mail 1 - Value",
        "E-mail 2 - Type",
        "E-mail 2 - Value",
        "IM 1 - Type",
        "IM 1 - Service",
        "IM 1 - Value",
        "Phone 1 - Type",
        "Phone 1 - Value",
        "Phone 2 - Type",
        "Phone 2 - Value",
        "Phone 3 - Type",
        "Phone 3 - Value",
        "Phone 4 - Type",
        "Phone 4 - Value",
        "Address 1 - Type",
        "Address 1 - Formatted",
        "Address 1 - Street",
        "Address 1 - City",
        "Address 1 - PO Box",
        "Address 1 - Region",
        "Address 1 - Postal Code",
        "Address 1 - Country",
        "Address 1 - Extended Address",
        "Address 2 - Type",
        "Address 2 - Formatted",
        "Address 2 - Street",
        "Address 2 - City",
        "Address 2 - PO Box",
        "Address 2 - Region",
        "Address 2 - Postal Code",
        "Address 2 - Country",
        "Address 2 - Extended Address",
        "Organization 1 - Type",
        "Organization 1 - Name",
        "Organization 1 - Yomi Name",
        "Organization 1 - Title",
        "Organization 1 - Department",
        "Organization 1 - Symbol",
        "Organization 1 - Location",
        "Organization 1 - Job Description",

        "Relation 1 - Type",
        "Relation 1 - Value",
        "Relation 2 - Type",
        "Relation 2 - Value",

        "Website 1 - Type",
        "Website 1 - Value",

        "Event 1 - Type",
        "Event 1 - Value",
        "Event 2 - Type",
        "Event 2 - Value",

        "Custom Field 1 - Type",
        "Custom Field 1 - Value"
    };

    static final String[] ENTRY1 = {
        "((Prefix)) First Midlle Last suffix",
        "First", "Midlle", "Last", "", "Phonetic last", "",
        "Phonetic last", "((Prefix))", "suffix", "", "", "", "",

        "2019-01-01",

        "", "", "", "", "", "", "", "", "", "",

        "\"NOTE LIGNE1\nNOTE LIGNE2\n\"",
        "* My Contacts ::: [£]Amis ::: [£]Family ::: * Starred",

        "Home", "Email2@X.X",
        "* Work", "Email1@X.X",

        "",

        "Google Talk", "test@test.xom",
        "Main", "+41 44345678",
        "Home", "+41 33345678",
        "Mobile", "22345678",
        "Work", "11345678",

        "Work", "\"Street\nPOBOx\nNeighborhood, City, State/Province ZIP/Postal Code\nCountry/Region\n\"",
        "Street", "City", "POBOx", "State/Province", "ZIP/Postal Code", " Country/Region", "Neighborhood",

        "Home", "\"Adress2\nAdress2\nAdress2, Adress2, Adress2 Adress2\nAdress2\"",
        "Adress2", "Adress2", "Adress2", "Adress2", "Adress2", "Adress2", "Adress2", "",

        "Company", "", "JobTitle", "", "", "", "",

        "Child", "relationShip2", "Spouse", "relationShip1",

        "Profile", "http://test.com",

        "Anniversary", "2019-01-02",

        "Custom", "2019-01-03",

        "Custom1 Type", "Custom1 Value"
        };

}
