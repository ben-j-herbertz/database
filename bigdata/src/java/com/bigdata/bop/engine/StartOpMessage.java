package com.bigdata.bop.engine;

import java.io.Serializable;
import java.util.UUID;

import com.bigdata.bop.PipelineType;

/**
 * A message sent to the {@link IQueryClient} when an operator begins executing
 * for some chunk of inputs (an operator on a node against a shard for some
 * binding set chunks generated by downstream operator(s)).
 * 
 * @author <a href="mailto:thompsonbry@users.sourceforge.net">Bryan Thompson</a>
 * @version $Id$
 * 
 * @todo both {@link StartOpMessage} and {@link HaltOpMessage} should be either
 *       versioned data structures or pure interfaces.
 */
public class StartOpMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** The query identifier. */
    final public UUID queryId;

    /** The operator identifier. */
    final public int bopId;

    /** The index partition identifier. */
    final public int partitionId;

    /** The node on which the operator will execute. */
    final public UUID runningOnServiceId;

    /**
     * The #of {@link IChunkMessage} accepted as the input for the operator.
     * <p>
     * Note: This should be ONE (1) unless {@link IChunkMessage} are being
     * combined for the operator. {@link PipelineType#OneShot} operators often
     * process multiple {@link IChunkMessage}s at once.
     */
    final public int nmessages;

    public StartOpMessage(final UUID queryId, final int opId,
            final int partitionId, final UUID runningOnServiceId,
            final int nmessages) {

        if (queryId == null)
            throw new IllegalArgumentException();
        
        if (nmessages <= 0)
            throw new IllegalArgumentException();
        
        this.queryId = queryId;
        
        this.bopId = opId;
    
        this.partitionId = partitionId;
        
        this.runningOnServiceId = runningOnServiceId;
        
        this.nmessages = nmessages;
    
    }

    public String toString() {
        return getClass().getName() + "{queryId=" + queryId + ",bopId=" + bopId
                + ",partitionId=" + partitionId + ",serviceId=" + runningOnServiceId
                + ",nchunks=" + nmessages + "}";
    }

}
