# Ride Hailing – Low-Level Design (Java)

A console-based ride-hailing app that demonstrates SOLID principles and the Strategy pattern. Riders and drivers are registered, a ride is matched to a driver using a pluggable matching strategy, and the fare is calculated with a pluggable pricing strategy.

## Features

- Register riders and drivers
- View available drivers
- Request a ride (auto-matched to a driver)
- Complete a ride and receive a fare receipt
- Track ride status: `REQUESTED → ASSIGNED → COMPLETED` (or `CANCELLED`)

## Project Structure

```
src/
├── Main.java                       Console menu and composition root
├── model/                          Rider, Driver, Ride, FareReceipt, Location,
│                                   RideStatus, VehicleType
├── strategy/
│   ├── RideMatchingStrategy        Interface
│   │   ├── NearestDriverStrategy
│   │   └── LeastActiveDriverStrategy
│   └── FareStrategy                Interface
│       ├── DefaultFareStrategy
│       └── PeakHourFareStrategy    Wraps another FareStrategy with a surge multiplier
└── service/                        RiderService, DriverService, RideService
```

## Build and Run

Requires JDK 17+.

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

## Menu

```
1. Add Rider            5. Complete Ride
2. Add Driver           6. View Rides
3. View Available Drivers   7. Exit
4. Request Ride
```

Locations are entered as `x` and `y` coordinates. Distances are Euclidean and treated as km. Invalid input is caught and re-prompted, and service errors are printed instead of crashing the app.

## Swapping Strategies

Strategies are injected into `RideService` in one place, the composition root in `Main.java`:

```java
RideMatchingStrategy matching = new NearestDriverStrategy();   // or new LeastActiveDriverStrategy()
FareStrategy fare = new DefaultFareStrategy();                 // or new PeakHourFareStrategy(new DefaultFareStrategy(), 1.5)
```

To add a new rule, implement `RideMatchingStrategy` or `FareStrategy` and pass it in. No other class changes.

## Default Pricing

`fare = base + (per-km rate × distance)`

| Vehicle | Base (Rs.) | Per km (Rs.) |
|---------|-----------|--------------|
| BIKE    | 20        | 8            |
| AUTO    | 30        | 12           |
| CAR     | 50        | 18           |

`PeakHourFareStrategy` multiplies the wrapped strategy's fare by a surge factor (≥ 1.0).

## Design Notes

| Principle | Where |
|-----------|-------|
| SRP | One service per concern; fare logic lives only in `FareStrategy` implementations |
| OCP / LSP | New strategies are added as classes and are interchangeable |
| ISP | Each strategy interface has a single method |
| DIP | `RideService` depends on the strategy interfaces, injected via its constructor |
| DRY | `PeakHourFareStrategy` reuses a base strategy instead of duplicating rates |
| KISS / YAGNI | In-memory maps, no persistence or extra layers |
| Law of Demeter | `driver.distanceTo(...)` and `ride.getVehicleType()` avoid method chains |

## Behaviour Notes

- `Ride` enforces its own status transitions, so illegal changes (e.g. completing a ride twice) throw an error.
- If no driver is available, the ride is recorded as `CANCELLED`.
- `RideService.cancelRide` exists but is not in the menu, since the spec's menu has no cancel option.
- `findDriver` returns `null` when no driver matches, as the spec's interface returns `Driver`.

