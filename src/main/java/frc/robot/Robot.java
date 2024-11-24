// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the manifest
 * file in the resource
 * directory. 
 */
public class Robot extends TimedRobot {
  private final CANSparkMax sol1 = new CANSparkMax(1, MotorType.kBrushed);
  private final CANSparkMax sol2 = new CANSparkMax(2, MotorType.kBrushed);
  private final CANSparkMax sag1 = new CANSparkMax(3, MotorType.kBrushed);
  private final CANSparkMax sag2 = new CANSparkMax(4, MotorType.kBrushed);
  private final MotorControllerGroup sol = new MotorControllerGroup(sol1, sol2);
  private final MotorControllerGroup sag = new MotorControllerGroup(sag1, sag2);
  private final CANSparkMax shooter1 = new CANSparkMax(5, MotorType.kBrushed);
  private final CANSparkMax shooter2 = new CANSparkMax(6, MotorType.kBrushed);
  private final CANSparkMax shooter3 = new CANSparkMax(12, MotorType.kBrushed);

  private final Joystick joy1 = new Joystick(0);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(sol::set, sag::set);
  private final Timer m_timer = new Timer();


  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
     SendableRegistry.addChild(m_robotDrive, sol);
    SendableRegistry.addChild(m_robotDrive, sag);

    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    sag.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    m_robotDrive.setMaxOutput(1);
    m_robotDrive.arcadeDrive(-joy1.getRawAxis(1), joy1.getRawAxis(4));
    // a pozitif değer içeri aldı


    
    if (joy1.getRawButton(4)) {
      SetFeeder(0.7);
      SetShooter(0.7);

    }else if (joy1.getRawButton(3)) {

      SetShooter(-0.9);
      SetFeeder(-0.9);
    } else if (joy1.getRawButton(6)) {

      SetShooter(-0.7);
    }  else if (joy1.getRawButton(1)) {

      SetFeeder(0.7);
    } else {
      SetFeeder(0);
      SetShooter(0);
    }
  }

  private void SetFeeder(double speed) {
    shooter1.set(speed);
    shooter2.set(speed);
  }

  private void SetShooter(double speed) {
    shooter3.set(speed*2);
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
