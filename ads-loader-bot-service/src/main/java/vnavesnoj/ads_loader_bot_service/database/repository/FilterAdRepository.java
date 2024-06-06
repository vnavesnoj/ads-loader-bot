package vnavesnoj.ads_loader_bot_service.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vnavesnoj.ads_loader_bot_common.database.entity.FilterAd;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
public interface FilterAdRepository extends JpaRepository<FilterAd, Long> {

    Page<FilterAd> findAllByFilterId(Long id, Pageable pageable);

    @Query("""
            SELECT fa
            FROM FilterAd fa
            JOIN FETCH fa.ad a
            JOIN FETCH fa.filter f
            JOIN FETCH f.user u
            WHERE u.id = :id
                        """)
    Page<FilterAd> findAllByUserId(Long id, Pageable pageable);

    @Query("""
            SELECT fa
            FROM FilterAd fa
            JOIN FETCH fa.ad a
            JOIN FETCH fa.filter f
            JOIN FETCH f.user u
            WHERE f.name = :name
            AND
            u.id = :userId
            """)
    Page<FilterAd> findAllByFilterNameAndUserId(String name, Long userId, Pageable pageable);
}
