package org.github.dabson10.ecomerce.descuentos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DescuentoRepository  extends JpaRepository<Descuentos, UUID> {
}
