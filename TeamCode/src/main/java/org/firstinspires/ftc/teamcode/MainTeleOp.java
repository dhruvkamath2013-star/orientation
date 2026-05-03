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

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {

            // Drive: left stick Y = forward/back, right stick X = turn
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;

            double leftPower  = drive + turn;
            double rightPower = drive - turn;

            double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));
            if (max > 1.0) {
                leftPower  /= max;
                rightPower /= max;
            }

            frontLeft.setPower(leftPower);
            frontRight.setPower(rightPower);

            // Arm: A = up, B = down
            if (gamepad1.a) {
                armMotor.setPower(0.5);
            } else if (gamepad1.b) {
                armMotor.setPower(-0.5);
            } else {
                armMotor.setPower(0.0);
            }

            telemetry.addData("Drive", "%.2f", drive);
            telemetry.addData("Turn",  "%.2f", turn);
            telemetry.addData("Arm",   "%.2f", armMotor.getPower());
            telemetry.update();
        }
    }
}
