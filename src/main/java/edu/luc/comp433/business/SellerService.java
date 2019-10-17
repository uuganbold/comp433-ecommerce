package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.business.dto.SellerDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.OrderItem;
import edu.luc.comp433.model.Seller;

import java.util.List;

public interface SellerService {

    void notifySales(Seller seller, OrderItem item);

    SellerDTO getSeller(long id);

    SellerDTO createSeller(SellerDTO dto) throws DuplicatedEntryException;

    List<SellerDTO> listAll();

    void save(SellerDTO dto) throws EntryNotFoundException, DuplicatedEntryException;

    void delete(long id) throws EntryNotFoundException, NotRemovableException;

    List<AddressDTO> listAddresses(long id) throws EntryNotFoundException;

    AddressDTO addAddress(long id, AddressDTO dto) throws EntryNotFoundException;

    void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException;
}
