package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor armMotor;

    @Override
    public void runOpMode() {
        frontLeft  = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        armMotor   = hardwareMap.get(DcMotor.class, "armMotor");

        // Reverse left so both sides drive forward with positive power
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // --- Drive ---
            // Left stick Y  = forward / backward
            // Left stick X  = attempted strafe (NOTE: traction wheels cannot strafe;
            //                 this axis has no lateral effect with the current wheel setup)
            // Right stick X = rotation
            double drive  = -gamepad1.left_stick_y;
            double turn   =  gamepad1.right_stick_x;

            double leftPower  = drive + turn;
            double rightPower = drive - turn;

            // Normalize so neither motor exceeds ±1
            double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
            if (max > 1.0) {
                leftPower  /= max;
                rightPower /= max;
            }

            frontLeft.setPower(leftPower);
            frontRight.setPower(rightPower);

            // --- Arm (UltraPlanetary HD Hex, 60:1) ---
            // A = up, B = down
            if (gamepad1.a) {
                armMotor.setPower(0.5);
            } else if (gamepad1.b) {
                armMotor.setPower(-0.5);
            } else {
                armMotor.setPower(0.0);
            }

            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Turn",  "%.2f", turn);
            telemetry.addData("FL Power", "%.2f", frontLeft.getPower());
            telemetry.addData("FR Power", "%.2f", frontRight.getPower());
            telemetry.addData("Arm Power", "%.2f", armMotor.getPower());
            telemetry.update();
        }
    }
}
