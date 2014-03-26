package com.googlecode.cchlib.xutil.google.googlecontact.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.googlecode.cchlib.xutil.google.googlecontact.util.GoogleContactType;
import com.googlecode.cchlib.xutil.google.googlecontact.util.Header;

public class GoogleContact implements GoogleContactType {

    private static final long serialVersionUID = 1L;

    private String name;
    private String givenName;
    private String additionalName;
    private String familyName;
    private String yomiName;
    private String givenNameYomi;
    private String additionalNameYomi;
    private String familyNameYomi;
    private String namePrefix;
    private String nameSuffix;
    private String initials;
    private String nickname;
    private String shortName;
    private String maidenName;
    private String birthday;
    private String gender;
    private String location;
    private String billingInformation;
    private String directoryServer;
    private String mileage;
    private String occupation;
    private String hobby;
    private String sensitivity;
    private String priority;
    private String subject;
    private String notes;
    private String groupMembership;

    private final List<BasicEntry>        emails        = new ArrayList<>();
    private final List<IMEntry>           iMs           = new ArrayList<>();
    private final List<BasicEntry>        phones        = new ArrayList<>();
    private final List<AddressEntry>      addresses     = new ArrayList<>();
    private final List<OrganizationEntry> organizations = new ArrayList<>();
    private final List<BasicEntry>        websites      = new ArrayList<>();
    private final List<BasicEntry>        customFields  = new ArrayList<>();
    private final List<BasicEntry>        relations     = new ArrayList<>();
    private final List<BasicEntry>        events        = new ArrayList<>();

    public final String getName()
    {
        return name;
    }
    @Header("Name")
    public final void setName( final String name )
    {
        this.name = name;
    }

    public final String getGivenName()
    {
        return givenName;
    }
    @Header("Given Name")
    public final void setGivenName( final String givenName )
    {
        this.givenName = givenName;
    }

    public final String getAdditionalName()
    {
        return additionalName;
    }
    @Header("Additional Name")
    public final void setAdditionalName( final String additionalName )
    {
        this.additionalName = additionalName;
    }

    public final String getFamilyName()
    {
        return familyName;
    }
    @Header("Family Name")
    public final void setFamilyName( final String familyName )
    {
        this.familyName = familyName;
    }

    public final String getYomiName()
    {
        return yomiName;
    }
    @Header("Yomi Name")
    public final void setYomiName( final String yomiName )
    {
        this.yomiName = yomiName;
    }

    public final String getGivenNameYomi()
    {
        return givenNameYomi;
    }
    @Header("Given Name Yomi")
    public final void setGivenNameYomi( final String givenNameYomi )
    {
        this.givenNameYomi = givenNameYomi;
    }

    public final String getAdditionalNameYomi()
    {
        return additionalNameYomi;
    }
    @Header("Additional Name Yomi")
    public final void setAdditionalNameYomi( final String additionalNameYomi )
    {
        this.additionalNameYomi = additionalNameYomi;
    }

    public final String getFamilyNameYomi()
    {
        return familyNameYomi;
    }
    @Header("Family Name Yomi")
    public final void setFamilyNameYomi( final String familyNameYomi )
    {
        this.familyNameYomi = familyNameYomi;
    }

    public final String getNamePrefix()
    {
        return namePrefix;
    }
    @Header("Name Prefix")
    public final void setNamePrefix( final String namePrefix )
    {
        this.namePrefix = namePrefix;
    }

    public final String getNameSuffix()
    {
        return nameSuffix;
    }
    @Header("Name Suffix")
    public final void setNameSuffix( final String nameSuffix )
    {
        this.nameSuffix = nameSuffix;
    }

    public final String getInitials()
    {
        return initials;
    }
    @Header("Initials")
    public final void setInitials( final String initials )
    {
        this.initials = initials;
    }

    public final String getNickname()
    {
        return nickname;
    }
    @Header("Nickname")
    public final void setNickname( final String nickname )
    {
        this.nickname = nickname;
    }

    public final String getShortName()
    {
        return shortName;
    }
    @Header("Short Name")
    public final void setShortName( final String shortName )
    {
        this.shortName = shortName;
    }

    public final String getMaidenName()
    {
        return maidenName;
    }
    @Header("Maiden Name")
    public final void setMaidenName( final String maidenName )
    {
        this.maidenName = maidenName;
    }

    public final String getBirthday()
    {
        return birthday;
    }
    @Header("Birthday")
    public final void setBirthday( final String birthday )
    {
        this.birthday = birthday;
    }

    public final String getGender()
    {
        return gender;
    }
    @Header("Gender")
    public final void setGender( final String gender )
    {
        this.gender = gender;
    }

    public final String getLocation()
    {
        return location;
    }
    @Header("Location")
    public final void setLocation( final String location )
    {
        this.location = location;
    }

    public final String getBillingInformation()
    {
        return billingInformation;
    }
    @Header("Billing Information")
    public final void setBillingInformation( final String billingInformation )
    {
        this.billingInformation = billingInformation;
    }

    public final String getDirectoryServer()
    {
        return directoryServer;
    }
    @Header("Directory Server")
    public final void setDirectoryServer( final String directoryServer )
    {
        this.directoryServer = directoryServer;
    }

    public final String getMileage()
    {
        return mileage;
    }
    @Header("Mileage")
    public final void setMileage( final String mileage )
    {
        this.mileage = mileage;
    }

    public final String getOccupation()
    {
        return occupation;
    }
    @Header("Occupation")
    public final void setOccupation( final String occupation )
    {
        this.occupation = occupation;
    }

    public final String getHobby()
    {
        return hobby;
    }
    @Header("Hobby")
    public final void setHobby( final String hobby )
    {
        this.hobby = hobby;
    }

    public final String getSensitivity()
    {
        return sensitivity;
    }
    @Header("Sensitivity")
    public final void setSensitivity( final String sensitivity )
    {
        this.sensitivity = sensitivity;
    }

    public final String getPriority()
    {
        return priority;
    }
    @Header("Priority")
    public final void setPriority( final String priority )
    {
        this.priority = priority;
    }

    public final String getSubject()
    {
        return subject;
    }
    @Header("Subject")
    public final void setSubject( final String subject )
    {
        this.subject = subject;
    }

    public final String getNotes()
    {
        return notes;
    }
    @Header("Notes")
    public final void setNotes( final String notes )
    {
        this.notes = notes;
    }

    public final String getGroupMembership()
    {
        return groupMembership;
    }
    @Header("Group Membership")
    public final void setGroupMembership( final String groupMembership )
    {
        this.groupMembership = groupMembership;
    }

    public final Collection<BasicEntry> getEmails()
    {
        return emails;
    }
    @Header("E-mail") // "E-mail 1 - Type"
    public final void addEmail( final BasicEntry email )
    {
        this.emails.add( email );
    }

    public final Collection<IMEntry> getiMs()
    {
        return iMs;
    }
    @Header("IM")
    public final void addiM( final IMEntry iM )
    {
        this.iMs.add( iM );
    }

    public final Collection<BasicEntry> getPhones()
    {
        return phones;
    }
    @Header("Phone")
    public final void addPhone( final BasicEntry phone )
    {
        this.phones.add( phone );
    }

    public final Collection<AddressEntry> getAddresses()
    {
        return addresses;
    }
    @Header(value="Address")
    public final void addAddress( final AddressEntry address )
    {
        this.addresses.add( address );
    }

    public final Collection<OrganizationEntry> getOrganizations()
    {
        return organizations;
    }
    @Header("Organization") // "Organization 1 - Type"
    public final void addOrganization( final OrganizationEntry organization )
    {
        this.organizations.add( organization );
    }

    public final Collection<BasicEntry> getWebsites()
    {
        return websites;
    }
    @Header("Website")
    public final void addWebsite( final BasicEntry website )
    {
        this.websites.add( website );
    }

    public final Collection<BasicEntry> getCustomFields()
    {
        return customFields;
    }
    @Header("Custom Field")
    public final void addCustomField( final BasicEntry customField )
    {
        this.customFields .add( customField );
    }

    public final Collection<BasicEntry> getRelations()
    {
        return relations;
    }
    @Header("Relation") // "Relation 1 - Type", "Relation 1 - Value"
    public final void addRelation( final BasicEntry relation )
    {
        this.relations.add( relation );
    }

    public final Collection<BasicEntry> getEvents()
    {
        return events;
    }
    @Header("Event") // "Event 1 - Type", "Event 1 - Value"
    public final void addEvent( final BasicEntry event )
    {
        this.events .add( event );
    }

    public final static GoogleContactType newGoogleContactType( final Class<?> clazz )
            throws IllegalArgumentException
    {
        if( BasicEntry.class.isAssignableFrom( clazz ) ) {
            if( IMEntry.class.isAssignableFrom( clazz ) ) {
                return new IMEntryImpl();
             }
            return new BasicEntryImpl();
        } else if( AddressEntry.class.isAssignableFrom( clazz ) ) {
           return new AddressEntryImpl();
        } else if( OrganizationEntry.class.isAssignableFrom( clazz ) ) {
            return new OrganizationEntryImpl();
        } else if( GoogleContact.class.isAssignableFrom( clazz ) ) {
            return new GoogleContact();
        }
        throw new IllegalArgumentException( "Can not handle class : " + clazz );
    }
}

