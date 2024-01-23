package org.jembi.jempi.controller.interactions_processor;

import org.jembi.jempi.controller.interactions_processor.lib.CategorisedCandidates;
import org.jembi.jempi.controller.interactions_processor.processors.IOnNewInteractionProcessor;
import org.jembi.jempi.controller.interactions_processor.processors.IThresholdRangeSubProcessor;
import org.jembi.jempi.libmpi.LibMPI;
import org.jembi.jempi.shared.models.GoldenRecord;
import org.jembi.jempi.shared.models.Interaction;

import java.util.List;
import java.util.concurrent.ExecutionException;

public final class StandardInteractionProcessor extends BaseInteractionProcessor {
    public StandardInteractionProcessor(final String linkerId, final Interaction originalInteractionIn, final LibMPI libMPI) {
        super(linkerId, originalInteractionIn, libMPI);
    }
    @Override
    protected List<IThresholdRangeSubProcessor> getThresholdProcessors() {
        return this.processorRegistry.getThresholdProcessors(this.originalInteraction, this.linkerId);
    }

    @Override
    protected List<IOnNewInteractionProcessor> getOnNewInteractionProcessors() {
        return this.processorRegistry.getOnNewInteractionProcessors(this.linkerId);
    }

    public void onProcessCandidates(final Interaction interaction) throws ExecutionException, InterruptedException {
        List<GoldenRecord> candidateGoldenRecords = libMPI.findLinkCandidates(interaction.demographicData());
        List<CategorisedCandidates> categorisedCandidates = this.getCategorisedCandidates(candidateGoldenRecords);
        for (IThresholdRangeSubProcessor subProcessor: thresholdProcessors) {
            subProcessor.processCandidates(categorisedCandidates);
        }
    }

}