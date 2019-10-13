package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.business.dto.SellerDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.OrderItem;
import edu.luc.comp433.model.Seller;

import java.util.List;

public interface SellerService {

    void notifySales(Seller seller, OrderItem item);

    SellerDto getSeller(long id);

    SellerDto createSeller(SellerDto dto) throws DuplicatedEntryException;

    List<SellerDto> listAll();

    void save(SellerDto dto) throws EntryNotFoundException, DuplicatedEntryException;

    void delete(long id) throws EntryNotFoundException, NotRemovableException;

    List<AddressDto> listAddresses(long id) throws EntryNotFoundException;

    AddressDto addAddress(long id, AddressDto dto) throws EntryNotFoundException;

    void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException;
}
