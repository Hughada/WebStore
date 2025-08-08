/** package com.hughadatips.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "trip_image")
public class TripImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    private String mimeType;  // "image/jpeg", etc.
}

 **/
