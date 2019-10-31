package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.business.dto.SellerDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.Address;
import edu.luc.comp433.model.OrderItem;
import edu.luc.comp433.model.Seller;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;

    private AddressRepository addressRepository;

    public SellerServiceImpl(SellerRepository sellerRepository, AddressRepository addressRepository) {
        this.sellerRepository = sellerRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public SellerDTO getSeller(long id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if (seller == null) return null;
        return new SellerDTO(
                seller.getId(),
                seller.getName(),
                seller.getWebsite(),
                seller.getEmail()
        );
    }

    @Override
    public SellerDTO createSeller(SellerDTO dto) throws DuplicatedEntryException {
        Seller s = new Seller(dto.getName(), dto.getWebsite(), dto.getEmail());
        try {
            sellerRepository.save(s);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicatedEntryException("Sellers cannot have same name", dive);
        }
        dto.setId(s.getId());
        return dto;
    }

    @Override
    public List<SellerDTO> listAll() {
        List<SellerDTO> dtos = new ArrayList<>();
        sellerRepository.findAll().forEach(s -> dtos.add(new SellerDTO(s.getId(), s.getName(), s.getWebsite(), s.getEmail())));
        return dtos;
    }

    @Override
    public void save(SellerDTO dto) throws EntryNotFoundException, DuplicatedEntryException {
        Seller c = sellerRepository.findById(dto.getId()).orElseThrow(() -> new EntryNotFoundException("Seller not found with id:" + dto.getId()));
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setWebsite(dto.getWebsite());
        try {
            sellerRepository.save(c);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicatedEntryException("Sellers cannot have same name:" + dto.getName(), dive);
        }

    }

    @Override
    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        Seller c = sellerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Seller not found with id:" + id));
        try {
            sellerRepository.delete(c);
        } catch (DataIntegrityViolationException e) {
            throw new NotRemovableException("This seller not removable:" + id);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDTO> listAddresses(long id) throws EntryNotFoundException {
        Seller c = sellerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Seller not found with id:" + id));
        List<AddressDTO> dtos = new ArrayList<>();
        c.getAddresses().forEach(a -> dtos.add(new AddressDTO(a.getId(), a.getCountry(), a.getStreet(), a.getUnit(), a.getCity(), a.getState(), a.getZipcode(), a.getPhonenumber())));
        return dtos;
    }

    @Override
    @Transactional
    public AddressDTO addAddress(long id, AddressDTO dto) throws EntryNotFoundException {
        Seller c = sellerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Seller not found with id:" + id));
        Address a = new Address(dto.getCountry(), dto.getStreet(),
                dto.getUnit(), dto.getCity(), dto.getState(),
                dto.getZipcode(), dto.getPhonenumber());
        addressRepository.save(a);
        c.addAddress(a);
        sellerRepository.save(c);
        dto.setId(a.getId());
        return dto;
    }

    @Override
    public AddressDTO getAddress(long id, long addressId) {
        Seller c = sellerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Seller not found with id:" + id));
        return AddressDTO.of(c.getAddress(addressId));
    }

    @Override
    @Transactional
    public void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException {
        Seller c = sellerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Seller not found with id:" + id));
        Address a;
        if ((a = c.getAddress(addressId)) == null)
            throw new EntryNotFoundException("This seller (" + id + ") does have this address: " + addressId);
        try {
            c.removeAddress(a);
            sellerRepository.save(c);
        } catch (DataIntegrityViolationException dive) {
            throw new NotRemovableException("This address not removable:" + addressId);
        }

    }

    @Override
    public void notifySales(Seller seller, OrderItem item) {
        //TODO make some notification.
    }
}
