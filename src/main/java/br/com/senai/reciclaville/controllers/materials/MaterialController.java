package br.com.senai.reciclaville.controllers.materials;

import br.com.senai.reciclaville.models.DTOS.materials.MaterialResponseDTO;
import br.com.senai.reciclaville.models.entities.Materials;
import br.com.senai.reciclaville.services.materials.MaterialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<MaterialResponseDTO> create(@RequestBody MaterialResponseDTO materialDTO) throws Exception{
        Materials material = modelMapper.map(materialDTO, Materials.class);
        Materials createdMaterial = materialService.newMaterial(material);
        MaterialResponseDTO createdMaterialDTO = modelMapper.map(createdMaterial, MaterialResponseDTO.class);

        return ResponseEntity.ok(createdMaterialDTO);
    }

    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> findAllMaterials(){
        List<MaterialResponseDTO> materials = this.materialService.findAllMaterials().stream()
                .map(materials1 -> modelMapper.map(materials1, MaterialResponseDTO.class))
                .collect(Collectors.toList());
        return materials.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(materials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> findMaterial(@PathVariable Long id){
        Optional<Materials> material = materialService.findMaterialById(id);

        return material.map(materials -> {
            MaterialResponseDTO materialDTO = modelMapper.map(materials, MaterialResponseDTO.class);
            return ResponseEntity.ok(materialDTO);
        })
            .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> update(@PathVariable Long id, @RequestBody MaterialResponseDTO materialDTO) throws Exception{
        Materials material = modelMapper.map(materialDTO, Materials.class);
        Materials updateMaterial = this.materialService.updateMaterial(id, material);
        MaterialResponseDTO updateMaterialDTO = modelMapper.map(updateMaterial, MaterialResponseDTO.class);
        return ResponseEntity.ok(updateMaterialDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long id){
        this.materialService.deleteMaterial(id);
        return ResponseEntity.ok("Material deletado com sucesso!");
    }
}
