package com.example.demo.Repository;


import com.example.demo.Models.Text;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {

    //@formatter:off

    @Transactional
    @Modifying
    @Query(value = "UPDATE text_table set text = :text, " +
            "background = :background, " +
            "font = :font " +
            "where textid = :textid",
            nativeQuery = true)
    void updateResource(@Param("text") String text,
                        @Param("background") String background,
                        @Param("font") String font,
                        @Param("textid") int textid);

    //@formatter:on

}
