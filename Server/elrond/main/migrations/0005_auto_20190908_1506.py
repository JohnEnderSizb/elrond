# Generated by Django 2.2.5 on 2019-09-08 13:06

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0004_users'),
    ]

    operations = [
        migrations.RenameField(
            model_name='profile',
            old_name='email_addeess',
            new_name='email_address',
        ),
    ]
