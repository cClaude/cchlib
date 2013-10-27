/*
** -----------------------------------------------------------------------
** Nom           : cx/ath/choisnet/html/util/impl/FormItemImpl.java
** Description   :
**
**  3.02.035 2006.08.03 Claude CHOISNET - Version initiale
** -----------------------------------------------------------------------
**
** cx.ath.choisnet.html.util.impl.FormItemImpl
**
*/
package cx.ath.choisnet.html.util.impl;

/**
** <p>
** </p>
**
**
** @author Claude CHOISNET
** @since   3.02.035
** @version 3.02.035
*/
public class FormItemImpl
    implements cx.ath.choisnet.html.util.FormItem
{
/** serialVersionUID */
private static final long serialVersionUID = 1L;

/** */
private String name;

/** */
private String value;

/**
**
*/
public FormItemImpl() // --------------------------------------------------
{
 // empty
}

/**
**
*/
public FormItemImpl( // ---------------------------------------------------
    final String name,
    final String value
    )
{
 this.name  = name;
 this.value = value;
}

/**
**
*/
public FormItemImpl setName( final String name ) // -----------------------
{
 this.name  = name;

 return this;
}

/**
**
*/
@Override
public String getName() // ------------------------------------------------
{
 return this.name;
}

/**
**
*/
public FormItemImpl setValue( final String value ) // ---------------------
{
 this.value  = value;

 return this;
}

/**
**
*/
@Override
public String getValue() // -----------------------------------------------
{
 return this.value;
}

} // class

