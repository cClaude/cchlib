/*
** SimpleListEntry.java
*/
package jrpdk.util;

public class SimpleListEntry<E>
{
protected E                  element;
protected SimpleListEntry<E> next;

/**
**
*/
public SimpleListEntry( // ------------------------------------------------
    E element
    )
{
 this( element, null );
}

/**
**
*/
public SimpleListEntry( // ------------------------------------------------
    E                  element,
    SimpleListEntry<E> next
    )
{
 this.element   = element;
 this.next      = next;
}

/*
*
** Retourne le noeud suivant
*
/
protected Object _value() // -----------------------------------------------
{
 return this.value;
}

/
**
** Retourne le noeud suivant
*
/
protected SimpleListEntry _next() // ---------------------------------------
{
 return this.next;
}
*/

} // class

