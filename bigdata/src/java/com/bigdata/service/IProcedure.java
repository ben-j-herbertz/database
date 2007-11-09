/*

Copyright (C) SYSTAP, LLC 2006-2007.  All rights reserved.

Contact:
     SYSTAP, LLC
     4501 Tower Road
     Greensboro, NC 27410
     licenses@bigdata.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
/*
 * Created on Mar 15, 2007
 */

package com.bigdata.service;

import java.io.Serializable;

import com.bigdata.btree.IIndexWithCounter;
import com.bigdata.btree.IReadOnlyOperation;
import com.bigdata.journal.AbstractTask;
import com.bigdata.journal.ConcurrentJournal;
import com.bigdata.journal.Journal;

/**
 * An arbitrary index procedure to be executed on an {@link IDataService}.
 * <p>
 * Note: while this interface is {@link Serializable}, that provides only for
 * communicating state to the {@link IDataService}. If an instance of this
 * procedure will cross a network interface, then the implementation Class MUST
 * be available to the {@link IDataService} on which it will execute. This can
 * be as simple as bundling the procedure into a JAR that is part of the
 * CLASSPATH used to start a {@link DataService} or you can use downloaded code
 * with the JINI codebase mechanism (<code>java.rmi.server.codebase</code>).
 * <p>
 * Note: While we could define a "procedure" operation on multiple named
 * indices, clients of the {@link IDataService} API would be unable to exploit
 * that operation without unreasonable knowledge of the location of index
 * partitions throughout the federation. People requiring those semantics who
 * are operating against an embedded {@link Journal} can trivially realize them
 * by extending {@link AbstractTask}.
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 * 
 * @see IReadOnlyOperation
 * 
 * @todo refactor into the btree package, abstract away from the data services,
 *       and remove the exception declaration for
 *       {@link #apply(IIndexWithCounter)} and reconcile with the change to the
 *       {@link ConcurrentJournal}.
 */
public interface IProcedure extends Serializable {

    /**
     * Run the procedure.
     * <p>
     * Unisolated procedures have "auto-commit" ACID properties for the local
     * {@link IDataService} on which they execute, but DO NOT have distributed
     * ACID properties. In order for a distributed procedure to be ACID, the
     * procedure MUST be fully isolated.
     * 
     * @param ndx
     *            The index.
     * 
     * @return The result, which is entirely defined by the procedure
     *         implementation and which MAY be null. In general, this MUST be
     *         {@link Serializable} since it may have to pass across a network
     *         interface.
     */
    public Object apply(IIndexWithCounter ndx) throws Exception;
    
}
