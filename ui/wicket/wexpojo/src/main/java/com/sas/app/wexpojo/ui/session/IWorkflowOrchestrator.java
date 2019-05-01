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
package com.sas.app.wexpojo.ui.session;

import java.lang.*;


// -[KeepBeforeClass]-


// -[Class]-

/**
 * Class Name : IWorkflowOrchestrator
 * Diagram    : Session
 * Project    : WexPOJO - Wicket + exPOJO application core
 * Type       : interface
 * Interface for objects whose lifecylce is beyond that of a single request cycle and
 * that are attached to the session.
 * 
 * These objects are often used to store data that is collected from a user as they traverse
 * a workflow that involves multiple screens.
 * 
 * @author Chris Colman
 */
public abstract 
interface IWorkflowOrchestrator
{
// -[KeepWithinClass]-


// -[Fields]-


// -[Methods]-




/**
 * Detaches the workflow orchestrator.
 */
public abstract void detach();

}


