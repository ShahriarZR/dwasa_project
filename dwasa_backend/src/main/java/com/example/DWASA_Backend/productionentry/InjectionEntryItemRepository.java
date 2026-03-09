package com.example.DWASA_Backend.productionentry;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionEntryItemRepository extends JpaRepository<InjectionEntryItem, Long> {
	List<InjectionEntryItem> findAllByInjectionEntryIdAndDeletedAtIsNullOrderByIdAsc(Long injectionEntryId);

	List<InjectionEntryItem> findAllByInjectionEntryIdOrderByIdAsc(Long injectionEntryId);
}

