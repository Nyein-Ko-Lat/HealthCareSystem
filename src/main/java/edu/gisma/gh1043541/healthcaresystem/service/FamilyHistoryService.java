
package edu.gisma.gh1043541.healthcaresystem.service;

import edu.gisma.gh1043541.healthcaresystem.entity.FamilyHistory;
import edu.gisma.gh1043541.healthcaresystem.repository.FamilyHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyHistoryService implements IBaseService<FamilyHistory, Long> {

    private final FamilyHistoryRepository familyHistoryRepository;

    public FamilyHistoryService(FamilyHistoryRepository familyHistoryRepository) {
        this.familyHistoryRepository = familyHistoryRepository;
    }

    @Override
    public FamilyHistory save(FamilyHistory fh) {
        try{
            familyHistoryRepository.save(fh);
        }catch (Exception ex){

        }finally {
            return fh;
        }
    }

    @Override
    public void delete(Long familyHistoryID) {
        familyHistoryRepository.delete(familyHistoryID);
    }

    @Override
    public void delete(Long familyHistoryID, Long UpdatedBy) {
        familyHistoryRepository.delete(familyHistoryID,UpdatedBy);
    }

    @Override
    public FamilyHistory findById(Long familyHistoryID) {
        return familyHistoryRepository.findById(familyHistoryID);
    }

    @Override
    public List<FamilyHistory> findAll() {
        return familyHistoryRepository.findAll();
    }

    public List<FamilyHistory> findByPatientId(Long Patientid) {return familyHistoryRepository.findByPatientId(Patientid);}
}
