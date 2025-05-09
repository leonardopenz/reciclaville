package br.com.senai.reciclaville.services.materials;

import br.com.senai.reciclaville.models.entities.Materials;
import br.com.senai.reciclaville.repositories.materials.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    @Autowired
    MaterialRepository materialRepository;

    public Materials newMaterial(Materials material){
        return materialRepository.save(material);
    }

    public List<Materials> findAllMaterials(){
        return materialRepository.findAll();
    }

    public Optional<Materials> findMaterialById(Long id){
        return materialRepository.findById(id);
    }

    public Materials updateMaterial(Long id, Materials updateMaterial) throws Exception {
        Optional<Materials> material = materialRepository.findById(id);
        if(material.isPresent()){
            material.get().setNome(updateMaterial.getNome());
            material.get().setPercentualCompensacao(updateMaterial.getPercentualCompensacao());
            materialRepository.save(material.get());
        }
        return material.get();
    }

    public void deleteMaterial(Long id){
        materialRepository.deleteById(id);
    }
}
