package com.googlecode.cchlib.xutil.google.googlecontact.extension;

import java.io.Serializable;
import java.util.Collection;
import com.googlecode.cchlib.util.Wrappable;
import com.googlecode.cchlib.util.WrapperException;
import com.googlecode.cchlib.util.WrapperHelper;
import com.googlecode.cchlib.xutil.google.googlecontact.types.BasicEntry;
import com.googlecode.cchlib.xutil.google.googlecontact.types.GoogleContact;
import com.googlecode.cchlib.xutil.google.googlecontact.types.IMEntry;

public class SimplifiedGoogleContact implements Serializable { // $codepro.audit.disable largeNumberOfMethods

    private static final long serialVersionUID = 1L;

    private static final Wrappable<BasicEntry,SimpifiedBasicEntry> BASIC_ENTRY_WRAPPER = new Wrappable<BasicEntry,SimpifiedBasicEntry>(){
        @Override
        public SimpifiedBasicEntry wrap( final BasicEntry basicEntry ) throws WrapperException
        {
            return new SimpifiedBasicEntryImpl( basicEntry );
        }};

    private static final Wrappable<SimpifiedBasicEntry,BasicEntry> SIMPIFIED_BASIC_ENTRY_WRAPPER = new Wrappable<SimpifiedBasicEntry, BasicEntry>() {
        @Override
        public BasicEntry wrap( final SimpifiedBasicEntry simpifiedBasicEntry )
                throws WrapperException
        {
            return simpifiedBasicEntry.getBasicEntry();
        }};

    private static final Wrappable<SimpifiedIMEntry, IMEntry> SIMPIFIED_IM_ENTRY_WRAPPER = new Wrappable<SimpifiedIMEntry, IMEntry>(){
        @Override
        public IMEntry wrap( final SimpifiedIMEntry simpifiedIMEntry ) throws WrapperException
        {
            return simpifiedIMEntry.getIMEntry();
        }};

    private static final Wrappable<IMEntry, SimpifiedIMEntry> IM_ENTRY_WRAPPER = new Wrappable<IMEntry, SimpifiedIMEntry>(){
        @Override
        public SimpifiedIMEntry wrap( final IMEntry iMEntry ) throws WrapperException
        {
            return new SimpifiedIMEntryImpl( iMEntry );
        }};

    private final GoogleContact googleContact;

    public SimplifiedGoogleContact( final GoogleContact googleContact )
    {
        this.googleContact = googleContact;
    }

    public GoogleContact getGoogleContact()
    {
        return googleContact;
    }

    public String getName()
    {
        return googleContact.getName();
    }

    public final void setName( final String name )
    {
        googleContact.setName( name );
    }

    public final String getGivenName()
    {
        return googleContact.getGivenName();
    }

    public final void setGivenName( final String givenName )
    {
        googleContact.setGivenName( givenName );
    }

    public final String getAdditionalName()
    {
        return googleContact.getAdditionalName();
    }

    public final void setAdditionalName( final String additionalName )
    {
        googleContact.setAdditionalName( additionalName );
    }

    public final String getFamilyName()
    {
        return googleContact.getFamilyName();
    }

    public final void setFamilyName( final String familyName )
    {
        googleContact.setFamilyName( familyName );
    }

    public final String getYomiName()
    {
        return googleContact.getYomiName();
    }

    public final void setYomiName( final String yomiName )
    {
        googleContact.setYomiName( yomiName );
    }

    public final String getGivenNameYomi()
    {
        return googleContact.getGivenNameYomi();
    }

    public final void setGivenNameYomi( final String givenNameYomi )
    {
        googleContact.setGivenNameYomi( givenNameYomi );
    }

    public final String getAdditionalNameYomi()
    {
        return googleContact.getAdditionalNameYomi();
    }

    public final void setAdditionalNameYomi( final String additionalNameYomi )
    {
        googleContact.setAdditionalNameYomi( additionalNameYomi );
    }

    public final String getFamilyNameYomi()
    {
        return googleContact.getFamilyNameYomi();
    }

    public final void setFamilyNameYomi( final String familyNameYomi )
    {
        googleContact.setFamilyNameYomi( familyNameYomi );
    }

    public final String getNamePrefix()
    {
        return googleContact.getNamePrefix();
    }

    public final void setNamePrefix( final String namePrefix )
    {
        googleContact.setNamePrefix( namePrefix );
    }

    public final String getNameSuffix()
    {
        return googleContact.getNameSuffix();
    }

    public final void setNameSuffix( final String nameSuffix )
    {
        googleContact.setNameSuffix( nameSuffix );
    }

    public final String getInitials()
    {
        return googleContact.getInitials();
    }

    public final void setInitials( final String initials )
    {
        googleContact.setInitials( initials );
    }

    public final String getNickname()
    {
        return googleContact.getNickname();
    }

    public final void setNickname( final String nickname )
    {
        googleContact.setNickname( nickname );
    }

    public final String getShortName()
    {
        return googleContact.getShortName();
    }

    public final void setShortName( final String shortName )
    {
        googleContact.setShortName( shortName );
    }

    public final String getMaidenName()
    {
        return googleContact.getMaidenName();
    }

    public final void setMaidenName( final String maidenName )
    {
        googleContact.setMaidenName( maidenName );
    }

    public final String getBirthday()
    {
        return googleContact.getBirthday();
    }

    public final void setBirthday( final String birthday )
    {
        googleContact.setBirthday( birthday );
    }

    public final String getGender()
    {
        return googleContact.getGender();
    }

    public final void setGender( final String gender )
    {
        googleContact.setGender( gender );
    }

    public final String getLocation()
    {
        return googleContact.getLocation();
    }

    public final void setLocation( final String location )
    {
        googleContact.setLocation( location );
    }

    public final String getBillingInformation()
    {
        return googleContact.getBillingInformation();
    }

    public final void setBillingInformation( final String billingInformation )
    {
        googleContact.setBillingInformation( billingInformation );
    }

    public final String getDirectoryServer()
    {
        return googleContact.getDirectoryServer();
    }

    public final void setDirectoryServer( final String directoryServer )
    {
        googleContact.setDirectoryServer( directoryServer );
    }

    public final String getMileage()
    {
        return googleContact.getMileage();
    }

    public final void setMileage( final String mileage )
    {
        googleContact.setMileage( mileage );
    }

    public final String getOccupation()
    {
        return googleContact.getOccupation();
    }

    public final void setOccupation( final String occupation )
    {
        googleContact.setOccupation( occupation );
    }

    public final String getHobby()
    {
        return googleContact.getHobby();
    }

    public final void setHobby( final String hobby )
    {
        googleContact.setHobby( hobby );
    }

    public final String getSensitivity()
    {
        return googleContact.getSensitivity();
    }

    public final void setSensitivity( final String sensitivity )
    {
        googleContact.setSensitivity( sensitivity );
    }

    public final String getPriority()
    {
        return googleContact.getPriority();
    }

    public final void setPriority( final String priority )
    {
        googleContact.setPriority( priority );
    }

    public final String getSubject()
    {
        return googleContact.getSubject();
    }

    public final void setSubject( final String subject )
    {
        googleContact.setSubject( subject );
    }

    public final String getNotes()
    {
        return googleContact.getNotes();
    }

    public final void setNotes( final String notes )
    {
        googleContact.setNotes( notes );
    }

    public final String getGroupMembership()
    {
        return googleContact.getGroupMembership();
    }

    public final void setGroupMembership( final String groupMembership )
    {
        googleContact.setGroupMembership( groupMembership );
    }

    public final Collection<SimpifiedBasicEntry> getEmails()
    {
        return toSimpifiedBasicEntry( googleContact.getEmails() );
    }

    public final void addEmail( final SimpifiedBasicEntry email )
    {
        googleContact.addEmail( toBasicEntry( email ) );
    }

    public final Collection<SimpifiedBasicEntry> getPhones()
    {
        return toSimpifiedBasicEntry( googleContact.getPhones() );
    }

    public final void addPhone( final SimpifiedBasicEntry phone )
    {
        googleContact.addPhone( toBasicEntry( phone ) );
    }

    public final Collection<SimpifiedBasicEntry> getWebsites()
    {
        return toSimpifiedBasicEntry( googleContact.getWebsites() );
    }

    public final void addWebsite( final SimpifiedBasicEntry website )
    {
        googleContact.addWebsite( toBasicEntry( website ) );
    }

    public final Collection<SimpifiedBasicEntry> getCustomFields()
    {
        return toSimpifiedBasicEntry( googleContact.getCustomFields() );
    }

    public final void addCustomField( final SimpifiedBasicEntry customField )
    {
        googleContact.addCustomField( toBasicEntry( customField ) );
    }

    public final Collection<SimpifiedBasicEntry> getRelations()
    {
        return toSimpifiedBasicEntry( googleContact.getRelations() );
    }

    public final void addRelation( final SimpifiedBasicEntry relation )
    {
        googleContact.addRelation( toBasicEntry( relation ) );
    }

    public final Collection<SimpifiedBasicEntry> getEvents()
    {
        return toSimpifiedBasicEntry( googleContact.getEvents() );
    }

    public final void addEvent( final SimpifiedBasicEntry event )
    {
        googleContact.addEvent( toBasicEntry( event ) );
    }

//    private Collection<SimpifiedBasicEntry> toSimpifiedBasicEntry( final Collection<BasicEntry> entry )
//    {
//        Function<BasicEntry,SimpifiedBasicEntry> mapper;
//        //return WrapperHelper.toCollection( entry, BASIC_ENTRY_WRAPPER, SIMPIFIED_BASIC_ENTRY_WRAPPER  );
//        return entry.stream().map( mapper ).;//, BASIC_ENTRY_WRAPPER, SIMPIFIED_BASIC_ENTRY_WRAPPER  );
//    }
    private Collection<SimpifiedBasicEntry> toSimpifiedBasicEntry( final Collection<BasicEntry> entry )
    {
        return WrapperHelper.toCollection( entry, BASIC_ENTRY_WRAPPER, SIMPIFIED_BASIC_ENTRY_WRAPPER  );
    }

    private BasicEntry toBasicEntry( final SimpifiedBasicEntry simpifiedBasicEntry )
    {
        return simpifiedBasicEntry.getBasicEntry();
    }

    public final Collection<SimpifiedIMEntry> getiMs()
    {
        return toSimpifiedIMEntry( googleContact.getiMs() );
    }

    public final void addiM( final SimpifiedIMEntry iM )
    {
        googleContact.addiM( toSimpifiedIMEntry( iM ) );
    }

    private IMEntry toSimpifiedIMEntry( final SimpifiedIMEntry iM )
    {
        return iM.getIMEntry();
    }

    private Collection<SimpifiedIMEntry> toSimpifiedIMEntry( final Collection<IMEntry> iMs )
    {
        return WrapperHelper.toCollection( iMs, IM_ENTRY_WRAPPER, SIMPIFIED_IM_ENTRY_WRAPPER );
    }

//    public final Collection<AddressEntry> getAddresses()
//    {
//        return googleContact.getAddresses();
//    }
//
//    public final void addAddress( final AddressEntry address )
//    {
//        googleContact.addAddress( address );
//    }
//
//    public final Collection<OrganizationEntry> getOrganizations()
//    {
//        return googleContact.getOrganizations();
//    }
//
//    public final void addOrganization( final OrganizationEntry organization )
//    {
//        googleContact.addOrganization( organization );
//    }
/*
*/
}
