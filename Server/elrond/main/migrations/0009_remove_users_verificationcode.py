# Generated by Django 2.2.5 on 2019-09-08 19:15

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0008_users_verificationcode'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='users',
            name='verificationCode',
        ),
    ]
