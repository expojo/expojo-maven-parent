// -[KeepHeading]-


// -[Copyright]-

/**
 * (c) 2008,2019. Step Ahead Software Pty Ltd. All rights reserved.
 * Usage is governed by the terms of the Apache 2 License.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software.
 * To maintain code and model synchronization you may directly edit code in method bodies
 * and any sections starting with the 'Keep_*' marker. Make all other changes via Javelin.
 * See http://stepaheadsoftware.com for more details.
 */
package com.sas.app.wexpojo.bootstrap;

import java.lang.*;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;


// -[KeepBeforeClass]-
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.*;


// -[Class]-

/**
 * Class Name : BoostrapDataTable
 * Diagram    : Bootstrap capable components
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : concrete
 * Extends DataTable to add an appender that outputs bootstrap table (CSS) classes.
 * 
 * @author 
 */
public 
class BoostrapDataTable<T,S>
 extends DataTable
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-

/**
 * Constructs the new table
 */
public BoostrapDataTable(final String id, final List<? extends IColumn<T, S>> columns, final ISortableDataProvider<T, S> dataProvider, final int rowsPerPage)
{
	super(id, columns, dataProvider, rowsPerPage);

	add(new AttributeAppender("class", "table table-condensed table-hover table-stemless"));
}

}


