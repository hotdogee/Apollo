package org.bbop.apollo

import grails.transaction.Transactional
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
class CdsService {

    def featureService
    def cvTermService
    def featureRelationshipService

    public void setManuallySetTranslationStart(CDS cds, boolean manuallySetTranslationStart) {
        if (manuallySetTranslationStart && isManuallySetTranslationStart(cds)) {
            return;
        }
        if (!manuallySetTranslationStart && !isManuallySetTranslationStart(cds)) {
            return;
        }
        if (manuallySetTranslationStart) {
            featureService.addComment(cds, FeatureService.MANUALLY_SET_TRANSLATION_START)
//            cds.addComment(FeatureService.MANUALLY_SET_TRANSLATION_START);
        }
        if (!manuallySetTranslationStart) {
            featureService.deleteComment(cds, FeatureService.MANUALLY_SET_TRANSLATION_START)
//            cds.deleteComment(FeatureService.MANUALLY_SET_TRANSLATION_START);
        }
    }

    public boolean isManuallySetTranslationStart(CDS cds) {
        for (Comment comment : featureService.getComments(cds)) {
            if (comment.value.equals(FeatureService.MANUALLY_SET_TRANSLATION_START)) {
                return true;
            }
        }
        return false;
    }

    public StopCodonReadThrough getStopCodonReadThrough(CDS cds) {
        List<Feature> featureList = featureRelationshipService.getChildrenForFeature(cds, FeatureStringEnum.STOP_CODON_READTHROUGH)
        return featureList.size() == 1 ? (StopCodonReadThrough) featureList.get(0) : null
    }

    /**
     * TODO: is this right?  I think it should be CDS , not transcript?
     * TODO: is this just remove parents and children?
     * @param cds
     * @param stopCodonReadThrough
     * @return
     */
    def deleteStopCodonReadThrough(CDS cds, StopCodonReadThrough stopCodonReadThrough) {
        CVTerm partOfCvTerm = cvTermService.partOf
        CVTerm childCvTerm = cvTermService.getTerm(FeatureStringEnum.STOP_CODON_READTHROUGH)
        CVTerm parentCvTerm = cvTermService.getTerm(FeatureStringEnum.TRANSCRIPT)
        featureRelationshipService.deleteChildrenForType(cds, childCvTerm, partOfCvTerm)
        featureRelationshipService.deleteParentForType(stopCodonReadThrough, parentCvTerm, partOfCvTerm)
    }

    def deleteStopCodonReadThrough(CDS cds) {
        StopCodonReadThrough stopCodonReadThrough = getStopCodonReadThrough(cds);
        if (stopCodonReadThrough != null) {
            deleteStopCodonReadThrough(cds, stopCodonReadThrough);
        }

    }


    Transcript getTranscript(CDS cds) {
        List<Feature> featureList = featureRelationshipService.getParentForFeature(cds, FeatureStringEnum.TRANSCRIPT)
        featureList.size() == 1 ? (Transcript) featureList.get(0) : null
    }

    public StopCodonReadThrough createStopCodonReadThrough(CDS cds) {
//        Date date = new Date();

        String uniqueName = cds.getUniqueName() + FeatureStringEnum.STOP_CODON_READHTHROUGH_SUFFIX.value;
        StopCodonReadThrough stopCodonReadThrough = new StopCodonReadThrough(
                organism: cds.organism
                , uniqueName: uniqueName
                , isAnalysis: cds.isIsAnalysis()
                , isObsolete: cds.isIsObsolete()
        )
//        StopCodonReadThrough stopCodonReadThrough = new StopCodonReadThrough(cds.getOrganism(), uniqueName, cds.isAnalysis(),
//                cds.isObsolete(), null, cds.getConfiguration());
        FeatureLocation featureLocation = new FeatureLocation(
                sourceFeature: cds.featureLocation.sourceFeature
                , feature: stopCodonReadThrough
        ).save()

        stopCodonReadThrough.featureLocation = featureLocation
        stopCodonReadThrough.featureLocation.setStrand(cds.getStrand());

        stopCodonReadThrough.save(flush: true)

//        stopCodonReadThrough.setFeatureLocation(new FeatureLocation());
//        stopCodonReadThrough.getFeatureLocation().setSourceFeature(cds.getFeatureLocation().getSourceFeature());
//        stopCodonReadThrough.setTimeAccessioned(date);
//        stopCodonReadThrough.setTimeLastModified(date);
        return stopCodonReadThrough;
    }

    def setStopCodonReadThrough(CDS cds, StopCodonReadThrough stopCodonReadThrough,boolean replace=true) {
        CVTerm partOfCvTerm = cvTermService.partOf
        CVTerm stopCodonReadThroughCvTerm = cvTermService.getTerm(FeatureStringEnum.STOP_CODON_READTHROUGH)

        if(replace){
            for (FeatureRelationship fr : cds.getChildFeatureRelationships()) {
                if(partOfCvTerm==fr.type && stopCodonReadThroughCvTerm==fr.objectFeature.type){
                    fr.setSubjectFeature(stopCodonReadThrough);
                    return;
                }
            }
        }

        FeatureRelationship fr = new FeatureRelationship(
                type: partOfCvTerm
                ,objectFeature: cds
                ,subjectFeature: stopCodonReadThrough
                ,rank: 0 // TODO: Do we need to rank the order of any other transcripts?
        ).save(insert: true)
        cds.getChildFeatureRelationships().add(fr);
        stopCodonReadThrough.getParentFeatureRelationships().add(fr);

        stopCodonReadThrough.save()
        cds.save(flush:true)

    }
}