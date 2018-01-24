-- Create Database
CREATE DATABASE remote:localhost/wayoup root password plocal;

-- Create Classes
CREATE CLASS Session;
CREATE CLASS SessionHistory;
CREATE CLASS User ABSTRACT;
CREATE CLASS UserAccount EXTENDS User;
CREATE CLASS UserAccountRecovery;
CREATE CLASS UserBank;
CREATE CLASS UserBill;
CREATE CLASS UserExtAccount ABSTRACT EXTENDS User;
CREATE CLASS UserFacebook EXTENDS UserExtAccount;
CREATE CLASS UserSettings;
CREATE CLASS UserTwitter EXTENDS UserExtAccount;

-- Create Session properties
CREATE PROPERTY Session.user link User;
CREATE PROPERTY Session.token String;
CREATE PROPERTY Session.expiry Boolean;
CREATE PROPERTY Session.createDate Datetime;
CREATE INDEX Session.token UNIQUE;

-- Create SessionHistory properties
CREATE PROPERTY SessionHistory.user link User;
CREATE PROPERTY SessionHistory.token String;
CREATE PROPERTY SessionHistory.expiry Boolean;
CREATE PROPERTY SessionHistory.address String;
CREATE PROPERTY SessionHistory.headers String;
CREATE PROPERTY SessionHistory.loginDate Datetime;
CREATE PROPERTY SessionHistory.logoutDate Datetime;

-- Create User properties
CREATE PROPERTY User.bank embedded UserBank;
CREATE PROPERTY User.bill embedded UserBill;
CREATE PROPERTY User.settings embedded UserSettings;
CREATE PROPERTY User.blocked Boolean;
CREATE PROPERTY User.role String;
CREATE PROPERTY User.createDate Datetime;
CREATE PROPERTY User.modifyDate Datetime;

-- Create UserAccount properties
CREATE PROPERTY UserAccount.firstName String;
CREATE PROPERTY UserAccount.lastName String;
CREATE PROPERTY UserAccount.username String;
CREATE PROPERTY UserAccount.password String;
CREATE PROPERTY UserAccount.email String;
CREATE PROPERTY UserAccount.recovery embedded UserAccountRecovery;
CREATE PROPERTY UserAccount.confirmCode String;
CREATE PROPERTY UserAccount.confirmDate Datetime;
CREATE PROPERTY UserAccount.birthday Date;
CREATE PROPERTY UserAccount.phone String;
CREATE PROPERTY UserAccount.createDate Datetime;
CREATE INDEX UserAccount.username UNIQUE;

-- Create UserAccountRecovery properties
CREATE PROPERTY UserAccountRecovery.tempPassword String;
CREATE PROPERTY UserAccountRecovery.validDate Datetime;
CREATE PROPERTY UserAccountRecovery.createDate Datetime;

-- Create UserBank properties
CREATE PROPERTY UserBank.ownerName String;
CREATE PROPERTY UserBank.iban String;
CREATE PROPERTY UserBank.swiftBic String;
CREATE PROPERTY UserBank.createDate Datetime;
CREATE PROPERTY UserBank.modifyDate Datetime;

-- Create UserBill properties
CREATE PROPERTY UserBill.totalBalance Long;
CREATE PROPERTY UserBill.donationBalance Long;
CREATE PROPERTY UserBill.jobBalance Long;
CREATE PROPERTY UserBill.currency link Currency;
CREATE PROPERTY UserBill.operations Integer;
CREATE PROPERTY UserBill.donations Integer;
CREATE PROPERTY UserBill.jobs Integer;
CREATE PROPERTY UserBill.createDate Datetime;
CREATE PROPERTY UserBill.modifyDate Datetime;

-- Create UserExtAccount properties
CREATE PROPERTY UserExtAccount.extId String;
CREATE PROPERTY UserExtAccount.extType String;

-- Create UserFacebbok properties
CREATE PROPERTY UserFacebbok.firstName String;
CREATE PROPERTY UserFacebbok.lastName String;
CREATE PROPERTY UserFacebbok.lastName String;
CREATE PROPERTY UserSettings.email String;
CREATE PROPERTY UserSettings.link String;
CREATE PROPERTY UserSettings.verified Boolean;
CREATE PROPERTY UserSettings.gender String;
CREATE PROPERTY UserSettings.timezone String;
CREATE PROPERTY UserSettings.locale String;

-- Create UserSettings properties
CREATE PROPERTY UserSettings.locale String;
CREATE PROPERTY UserSettings.timezone String;
CREATE PROPERTY UserSettings.showPosition Boolean;

-- Create UserTwitter properties
CREATE PROPERTY UserTwitter.screenName String;
CREATE PROPERTY UserTwitter.name String;
