package com.jobapp.company.company;

import com.jobapp.company.exceptions.CompanyAlreadyExistException;
import com.jobapp.company.exceptions.CompanyNotFoundException;
import com.jobapp.company.models.Company;
import com.jobapp.company.models.CompanyRequestDTO;
import com.jobapp.company.models.CompanyResponse;
import com.jobapp.company.models.CompanyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {


    private final CompanyRepository companyRepository;

    private final ModelMapper modelMapper;

//    @Override
//    public Optional<Company> companyExist(Long id){
//        return companyRepository.findById(id);
//    }

    @Override
    public CompanyResponseDTO addCompany(CompanyRequestDTO companyDTO) {
        Company existCompany = companyRepository.findByCompanyName(companyDTO.getCompanyName());
        if(existCompany != null){
            log.warn("Company with name {} already exists", companyDTO.getCompanyName());
            throw new CompanyAlreadyExistException(companyDTO.getCompanyName());
        }
        Company companyToSave = modelMapper.map(companyDTO, Company.class);
        Company savedComp = companyRepository.save(companyToSave);
        CompanyResponseDTO companyResponseDTO = modelMapper.map(savedComp, CompanyResponseDTO.class);
        return companyResponseDTO;
    }

    @Override
    public CompanyResponse getAllCompanies(Integer pageNumber, Integer pageSize, String sortBy,
                                                 String sortOrder, String keyword) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Specification<Company> spec = null;
        if (keyword != null && !keyword.isEmpty()){
            spec = spec.and((root,query,criteriaBuilder)->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("companyName")), "%"+keyword.toLowerCase()+"%"));
        }

        Page<Company> companyPage = companyRepository.findAll(spec,pageDetails);

        List<Company> companies = companyPage.getContent();
        if (companies.isEmpty()){
            log.warn("Company list is empty");
            return null;
        }

        List<CompanyResponseDTO> companyDTOList =companies.stream()
                .map(company -> modelMapper
                        .map(company,CompanyResponseDTO.class))
                .toList();

        return new CompanyResponse(
                companyDTOList,
                companyPage.getNumber(),
                companyPage.getSize(),
                companyPage.getTotalElements(),
                companyPage.getTotalPages(),
                companyPage.isLast()
        );
    }

    @Override
    public Optional<CompanyResponseDTO> getCompanyWithId(Long id) {
        Company comp = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(id));

        log.info("Found company with id {}", id);
        return Optional.ofNullable(modelMapper.map(comp, CompanyResponseDTO.class));
    }

    @Override
    public CompanyResponseDTO updateCompanyWithId(Long id, CompanyRequestDTO updatedCompany) {
        Company companyToUpdate = companyRepository.findById(id)
                .orElseThrow(()->new CompanyNotFoundException(id));

            Company company= new Company();
            company.setCompanyName(updatedCompany.getCompanyName());
            company.setCompanyAddress(updatedCompany.getCompanyAddress());
            company.setCompanyPhone(updatedCompany.getCompanyPhone());
            company.setCompanyEmail(updatedCompany.getCompanyEmail());
            companyRepository.save(company);
            log.info("Company updated successfully");
            return modelMapper.map(companyToUpdate, CompanyResponseDTO.class);

    }

    @Override
    public boolean deleteCompany(Long id) {
        Optional<Company> companyToDelete = companyRepository.findById(id);
        if(companyToDelete.isPresent()){
            companyRepository.deleteById(id);
            log.info("Company deleted successfully");
            return true;
        }
        log.warn("Company not found with id: {}",id);
        return false;
    }
}
