package org.sid.radarService.web.grpc;

import org.sid.radarService.entities.Radar;
import org.sid.radarService.feign.InfractionFeignClient;
import org.sid.radarService.feign.RegistrationFeignClient;
import org.sid.radarService.model.Infraction;
import org.sid.radarService.model.Vehicle;
import org.sid.radarService.repositories.RadarRepository;
import org.sid.radarService.web.grpc.stubs.RadarOuterClass;
import org.sid.radarService.web.grpc.stubs.RadarServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Date;

@GrpcService
public class RadarGrpcService extends RadarServiceGrpc.RadarServiceImplBase {

    private final RadarRepository radarRepository;
    private final InfractionFeignClient infractionFeignClient;
    private final RegistrationFeignClient registrationFeignClient;

    public RadarGrpcService(RadarRepository radarRepository, InfractionFeignClient infractionFeignClient, RegistrationFeignClient registrationFeignClient) {
        this.radarRepository = radarRepository;
        this.infractionFeignClient = infractionFeignClient;
        this.registrationFeignClient = registrationFeignClient;
    }

    @Override
    public void detectInfraction(RadarOuterClass.DetectRequest request, StreamObserver<RadarOuterClass.Infraction> responseObserver) {
        Long radarId = request.getRadarId();
        Long vehicleId = request.getVehicleId();
        Double vehicleSpeed = request.getSpeed();

        if(radarRepository.existsById(radarId) && registrationFeignClient.isVehicleExistsById(vehicleId)){
            Radar radar = radarRepository.findById(radarId).get();
            Vehicle vehicle = registrationFeignClient.getVehicle(vehicleId);
            if (vehicleSpeed > radar.getMaxSpeed()){
                Infraction infraction = Infraction.builder()
                        .id(null)
                        .date(new Date().toString())
                        .vehicleSpeed(vehicleSpeed)
                        .radarMaxSpeed(radar.getMaxSpeed())
                        .fineAmount((vehicleSpeed - radar.getMaxSpeed()) * 100)
                        .radarId(radar.getId())
                        .vehicleId(vehicle.getId())
                        .vehicle(vehicle)
                        .radar(radar)
                        .build();
                infraction = infractionFeignClient.saveInfraction(infraction);

                infraction.setVehicle(vehicle);
                infraction.setRadar(radar);

                RadarOuterClass.Infraction response = RadarOuterClass.Infraction.newBuilder()
                        .setId(infraction.getId())
                        .setDate(infraction.getDate())
                        .setVehicleSpeed(infraction.getVehicleSpeed())
                        .setRadarMaxSpeed(infraction.getRadarMaxSpeed())
                        .setFineAmount(infraction.getFineAmount())
                        .setRadarId(infraction.getRadarId())
                        .setVehicleId(infraction.getVehicleId())
                        .setVehicle(RadarOuterClass.Vehicle.newBuilder()
                                .setId(infraction.getVehicle().getId())
                                .setRegNumber(infraction.getVehicle().getRegNumber())
                                .setBrand(infraction.getVehicle().getBrand())
                                .setModel(infraction.getVehicle().getModel())
                                .build())
                        .setRadar(RadarOuterClass.Radar.newBuilder()
                                .setId(infraction.getRadar().getId())
                                .setLongitude(infraction.getRadar().getLongitude())
                                .setLatitude(infraction.getRadar().getLatitude())
                                .setMaxSpeed(infraction.getRadar().getMaxSpeed())
                                .build())
                        .build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }

    }
}
